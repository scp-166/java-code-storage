package com.hpw.game.casino.manager;

import com.hpw.config.ComConfigParse;
import com.hpw.game.casino.Env;
import com.hpw.model.db.casino.component.manager.CasinoDaoManager;
import com.hpw.model.game.casino.bean.FriendApplication;
import com.hpw.model.game.casino.bean.RedisFriendInfo;
import com.hpw.model.game.casino.bean.FriendInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 好友管理
 *
 * @author lyl
 * @date 2020/9/27
 */
public class FriendManager {
    private static class Holder {
        public static final FriendManager INSTANCE = new FriendManager();
    }

    public static FriendManager getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 好友列表键, 用于集群内保证存放在同一个 slot 中
     */
    private static final String FRIEND_LIST_LUA_KEY = "{friends}:";

    /**
     * 获得存储好友信息键 <br>
     * 集群要求lua中的key都处于同一个 slot, 可以带上 {} , 会使用 {} 中的值计算 slot, 从而保存到同一个 slot 中
     *
     * @return 好友列表集群 lua 键
     */
    public String getFriendPrefix() {
        return FRIEND_LIST_LUA_KEY;
    }

    /**
     * 获得项目前缀 <br>
     *
     * @return 项目前缀
     */
    public String getProjectPrefix() {
        if (ComConfigParse.sys.needKeyPre) {
            return ComConfigParse.sys.KeyPre;
        }
        return "";
    }

    /**
     * 返回带有完整前缀头的 好友列表 集群lua前缀 <br>
     * 原因是 {@link com.hpw.model.redis.RedisSource} 的一些 api 是携带项目前缀
     *
     * @return 项目前缀+好友列表集群lua键
     */
    public String getFullFriendListPrefix() {
        return getProjectPrefix() + getFriendPrefix();
    }

    /**
     * <h2>好友申请</h2>
     *
     * @param applicantId 申请人id
     * @param toId        被申请人id
     * @return 如果是陌生人/没有好友申请记录, 添加申请后返回 1  <br>
     * 如果存在好友申请, 返回 0             <br>
     * 如果已是好友,返回 2                  <br>
     */
    public long applyForFriend(long applicantId, long toId, long createTime) {
        return Env.getGameSource().executeLua(Long.class, "lua/applyForFriend.lua",
                Arrays.asList(String.valueOf(toId), getFullFriendListPrefix()),
                RedisFriendInfo.createApplyingFriendInfo(applicantId, createTime));
    }


    /**
     * <h2>提交申请决定</h2>
     *
     * @param userId      用户id
     * @param applicantId 申请人id
     * @param isAgree     是否同意
     * @return 操作成功, 返回 1 <br>
     * 不存在申请记录,返回 -1
     */
    public long submitApplicationDecision(long userId, long applicantId, boolean isAgree) {
        List<String> keys = new ArrayList<>(2);
        keys.add(String.valueOf(userId));
        keys.add(String.valueOf(applicantId));
        keys.add(getFullFriendListPrefix());
        return Env.getGameSource().executeLua(Long.class, "lua/submitApplicationDecision.lua",
                keys, isAgree, System.currentTimeMillis());
    }

    /**
     * 补充前缀, 判断用户的好友列表是否存在
     *
     * @param userId 用户id
     * @return 存在返回 {@code true}
     */
    public boolean isFriendInfoExist(long userId) {
        Long ret;
        return (ret = Env.getGameSource().hlenWithPrefix(getFriendPrefix() + userId)) != null && ret != 0;
    }


    /**
     * 补充前缀, 以获得好友列表
     *
     * @param userId 用户id
     * @return 好友列表, hash
     */
    public Map<Object, Object> getFriendInfoMap(long userId) {
        // entries api 自带 FriendInfoUtil#projectPrefix()
        return Env.getGameSource().entriesWithPrefix(getFriendPrefix() + userId);
    }

    /**
     * 补充前缀, 以填充好友列表
     *
     * @param userId 用户id
     * @param values 待填充好友列表
     */
    public void hmsetFriendInfoMap(long userId, Map<?, ?> values) {
        // puts api 自带 FriendInfoUtil#projectPrefix()
        Env.getGameSource().putsWithPrefix(getFriendPrefix() + userId, values);
    }

    /**
     * 初始化用户的好友列表
     *
     * @param userId 用户id
     */
    public void initFriendListIfAbsent(long userId) {
        Logger logger = LoggerFactory.getLogger("friendCheck");
        Map<Object, Object> friendInfoMap = getFriendInfoMap(userId);
        logger.info("当前用户: {} 的好友列表为: {}", userId, friendInfoMap);

        // 不存在, 可能 redis lru 导致数据丢失
        if (!isFriendInfoExist(userId)) {

            // 初始好友列表
            List<FriendInfo> sourceFriends = CasinoDaoManager.getInstance().friendsDao.listFriendsFromDb(userId);
            logger.info("准备加载的初始好友列表: {}", sourceFriends);
            // 初始申请列表
            List<FriendApplication> sourceApplications = CasinoDaoManager.getInstance()
                    .friendApplicationDao.listFriendApplicationFromDb(userId);
            logger.info("准备加载的初始好友申请列表: {}", sourceApplications);

            Map<String, RedisFriendInfo> values = new HashMap<>(sourceFriends.size() + sourceApplications.size());
            // 如果该好友没有任何列表信息, 则填充一个默认值, 避免多次 刷 db
            if (CollectionUtils.isEmpty(values)) {
                values.put(String.valueOf(RedisFriendInfo.getEmptySubKey()), null);
            }

            RedisFriendInfo info;
            for (FriendInfo friendInfoItem : sourceFriends) {
                // 注意分辨当前记录是否为别人进行申请而进行记录的
                info = new RedisFriendInfo(friendInfoItem.getFriendId().equals(userId)
                        ? friendInfoItem.getUserId() : friendInfoItem.getFriendId(),
                        1, friendInfoItem.getUpdateTime());
                values.put(String.valueOf(friendInfoItem.getUserId()), info);
            }

            for (FriendApplication sourceApplication : sourceApplications) {
                info = new RedisFriendInfo(sourceApplication.getApplicantId(), 0, sourceApplication.getCreateTime());
                values.put(String.valueOf(sourceApplication.getApplicantId()), info);
            }

            logger.info("准备加载到 redis 的好友信息列: {}", values);
            hmsetFriendInfoMap(userId, values);
        }
    }
}
