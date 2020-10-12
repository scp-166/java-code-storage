package com.hpw.game.casino.service;


import com.github.javafaker.Faker;
import com.hpw.game.casino.manager.FriendManager;
import com.hpw.lib.service.AutomationService;
import com.hpw.lib.service.Executor;
import com.hpw.model.bean.ComUser;
import com.hpw.model.config.casino.ConfigParse;
import com.hpw.model.db.casino.component.manager.CasinoDaoManager;
import com.hpw.model.db.casino.inter.dao.IFriendApplicationDao;
import com.hpw.model.db.component.manager.DaoManager;
import com.hpw.model.game.casino.bean.RedisFriendInfo;
import com.hpw.model.game.casino.bean.FriendInfo;
import com.hpw.model.game.casino.bean.TemporaryUserInfo;
import com.hpw.model.game.casino.net.GameCmd;
import com.hpw.model.net.Request;
import com.hpw.model.net.Response;
import com.hpw.model.net.proto.casino.NetCasino;
import com.hpw.utils.ParamConvertUtil;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 好友服务
 *
 * @author lyl
 * @date 2020/9/16
 */
public class FriendService extends AutomationService {

    @Executor(command = GameCmd.NET_GET_FRIEND_LIST)
    public Response getFriendList(Request request) {
        Response response = Response.resultFrom(request);

        long userId = request.getUserId();

        FriendManager friendManager = FriendManager.getInstance();
        friendManager.initFriendListIfAbsent(userId);

        Map<Object, Object> currentFriendMap = friendManager.getFriendInfoMap(userId);
        List<TemporaryUserInfo> userInfoList = new ArrayList<>(currentFriendMap.size());
        currentFriendMap.forEach((sourceApplicantId, sourceApplicantInfo) -> {
            long applicantId = Long.parseLong((String) sourceApplicantId);
            if (applicantId == RedisFriendInfo.getEmptySubKey()) {
                return;
            }

            if (sourceApplicantInfo == null) {
                logger.warn("用户: {} 持有好友列表有 applicantId: {}　为null", userId, applicantId);
                return;
            }

            ComUser applicant = DaoManager.getInstance().comUserDao.findUser(applicantId);
            TemporaryUserInfo userInfo = new TemporaryUserInfo();
            userInfo.copyFrom(applicant);
            if (!userInfo.checkUserId()) {
                return;
            }

            userInfo.copyFrom(DaoManager.getInstance().currencyConsumptionDao.getCurrencyConsumption(applicantId));
            userInfoList.add(userInfo);
        });

        // todo {@author lyl} 排序
        //  通过最近有过私聊, 有过赠送行为, 按照时间最新的排前面, 可能要换表
        NetCasino.NetFriendInfoList.Builder builder = NetCasino.NetFriendInfoList.newBuilder();

        return response;
    }

    @Executor(command = GameCmd.NET_SEARCH_FRIEND)
    public Response searchFriend(Request request) {
        Response response = Response.resultFrom(request);

        // 暂时用 db

        return response;
    }

    @Executor(command = GameCmd.NET_FRIEND_APPLY)
    public Response friendApply(Request request) {
        Response response = Response.copyfrom(request);
        long applicantId = request.getUserId();
        long toId = request.dataToLong();

        if (applicantId == toId) {
            logger.warn("不能自己向自己申请好友: {}", applicantId);
            return response;
        }

        long currentTime = System.currentTimeMillis();
        long ret = FriendManager.getInstance().applyForFriend(applicantId, toId, currentTime);

        switch ((int) ret) {
            case 0:
                // 已经存在申请
                logger.info("已经存在申请: userId:{} toUserId:{}", applicantId, toId);
                response.setRet((short) ConfigParse.errorCode.ERR_ALREADY_FRIEND_APPLY.code);
                break;
            case 1:
                logger.info("保存申请: userId:{} toUserId:{}", applicantId, toId);
                CasinoDaoManager.getInstance().friendApplicationDao.addFriendApplication(toId, applicantId, currentTime);
            case 2:
                // 已是好友
                logger.info("已是好友: userId:{} toUserId:{}", applicantId, toId);
                response.setRet((short) ConfigParse.errorCode.ERR_ALREADY_FRIEND.code);
                break;
            default:
                logger.error("意料之外的错误, userId: {}, applicantId: {}, 请查看 redis 日志", toId, applicantId);
                response.setRet((short) ConfigParse.errorCode.ERROR_FRIEND_APPLY_UNKNOWN.code);
                break;
        }
        return response;
    }

    @Executor(command = GameCmd.NET_RESPONSE_2_FRIEND_APPLY)
    public Response responseFriendApply(Request request) {
        Response response = Response.resultFrom(request);
        List<Long> params = ParamConvertUtil.parse2LongList(request.dataToString(), ",");
        if (CollectionUtils.isEmpty(params)) {
            return response;
        }
        if (params.size() != 2) {
            return response;
        }

        long userId = request.getUserId();
        long applicantId = params.get(0);
        long isAgree = params.get(1);

        IFriendApplicationDao friendApplicationDao = CasinoDaoManager.getInstance().friendApplicationDao;

        FriendManager.getInstance().initFriendListIfAbsent(userId);

        long ret = FriendManager.getInstance().submitApplicationDecision(userId, applicantId, isAgree == 1);

        switch ((int) ret) {
            case 0:
                logger.info("已申请过");
                break;
            case 1:
                logger.info("响应好友申请成功操作: userId: {}, applicantId: {}, isAgree: {}", userId, applicantId,
                        isAgree);
                friendApplicationDao.deleteFriendApplication(userId, applicantId);
                if (isAgree == 1) {
                    // 添加好友信息
                    CasinoDaoManager.getInstance().friendsDao.addFriends2Db(userId, applicantId,
                            System.currentTimeMillis());
                }
                break;
            case 2:
                logger.info("响应好友申请成功操作: userId: {}, applicantId: {}, isAgree: {}," +
                        " 但是 userId 曾经向 applicant 发送过好友申请,需要删除", userId, applicantId, isAgree);
                friendApplicationDao.deleteFriendApplication(userId, applicantId);
                friendApplicationDao.deleteFriendApplication(applicantId, userId);
                break;
            default:
                logger.info("好友列表不存在");
                response.setRet((short) ConfigParse.errorCode.ERR_MISSING_FRIEND_APPLICATION.code);
                break;
        }
        return response;
    }

    public static void main(String[] args) {
        Faker faker = new Faker(Locale.CHINA);
        List<FriendInfo> friendList = new ArrayList<>();
        IntStream.range(0, 10).forEach(i -> {
            FriendInfo friendInfo = new FriendInfo();
            friendInfo.setUserId((long) i);
            friendInfo.setLastChatTime(randomLocalDateTime(-10, 10).toEpochSecond(ZoneOffset.UTC));
            friendInfo.setLastGiftGivingTime(randomLocalDateTime(-10, 10).toEpochSecond(ZoneOffset.UTC));
            friendInfo.setCreateTime(randomLocalDateTime(-10, 10).toEpochSecond(ZoneOffset.UTC));
            friendList.add(friendInfo);
        });

        List<FriendInfo> before = friendList.stream()
                .sorted(Comparator.comparing(FriendInfo::getLastChatTime)
                        .thenComparing(FriendInfo::getLastGiftGivingTime)
                        .thenComparing(FriendInfo::getCreateTime)
                        .reversed())
                .collect(Collectors.toList());

        friendList.forEach(item -> {
            System.out.printf("id: %d, lastChatTime: %d, lastGiftGivingTime: %d, createTime: %d%n", item.getUserId(),
                    item.getLastChatTime(), item.getLastGiftGivingTime(), item.getCreateTime());
        });

        System.out.println("+++++++++++++++++++++++++++++++++++++");

        before.forEach(item -> {
            System.out.printf("id: %d, lastChatTime: %d, lastGiftGivingTime: %d, createTime: %d%n", item.getUserId(),
                    item.getLastChatTime(), item.getLastGiftGivingTime(), item.getCreateTime());
        });
    }

    /**
     * 取范围日期的随机日期时间,不含边界
     *
     * @param startDay
     * @param endDay
     * @return
     */
    public static LocalDateTime randomLocalDateTime(int startDay, int endDay) {

        int plusMinus = 1;
        if (startDay < 0 && endDay > 0) {
            plusMinus = Math.random() > 0.5 ? 1 : -1;
            if (plusMinus > 0) {
                startDay = 0;
            } else {
                endDay = Math.abs(startDay);
                startDay = 0;
            }
        } else if (startDay < 0 && endDay < 0) {
            plusMinus = -1;

            //两个数交换
            startDay = startDay + endDay;
            endDay = startDay - endDay;
            startDay = startDay - endDay;

            //取绝对值
            startDay = Math.abs(startDay);
            endDay = Math.abs(endDay);

        }

        LocalDate day = LocalDate.now().plusDays(plusMinus * RandomUtils.nextInt(startDay, endDay));
        int hour = RandomUtils.nextInt(1, 24);
        int minute = RandomUtils.nextInt(0, 60);
        int second = RandomUtils.nextInt(0, 60);
        LocalTime time = LocalTime.of(hour, minute, second);
        return LocalDateTime.of(day, time);
    }

}
