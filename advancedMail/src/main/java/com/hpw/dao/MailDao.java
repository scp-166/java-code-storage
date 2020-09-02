package com.hpw.dao;

import com.hpw.bean.BroadcastMailInfoPO;
import com.hpw.bean.BroadcastMailRecordPO;
import com.hpw.bean.AdvancedMail;
import com.hpw.bean.NormalMailRecordPO;
import com.hpw.dao.mapper.NormalMailRecordDao;
import com.hpw.myenum.MailAttachmentStateEnum;
import com.hpw.myenum.MailSenderNameEnum;
import com.hpw.myenum.MailTypeEnum;
import com.hpw.utils.ComparatorUtil;
import com.hpw.utils.IdUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class MailDao {
    private static class Holder {
        public static MailDao instance = new MailDao();
    }

    public static MailDao getInstance() {
        return Holder.instance;
    }

    private Set<BroadcastMailInfoPO> broadcastMailSet = Collections.synchronizedSet(new HashSet<>());


    /**
     * 在线用户邮件缓存集合，各个用户持有的邮件列表具有 id 自动排序的属性
     */
    private Map<Long, List<AdvancedMail>> onlineUserMailWithIdAscMap = new ConcurrentHashMap<>();

    /**
     * 记录在线用户更新的 mail Id
     */
    private Map<Long, Set<Long>> onlineUserUpdateMailIdSetMap = new ConcurrentHashMap<>();

    /**
     * 记录在线用户删除的 mail Id
     */
    private Map<Long, Set<Long>> onlineUserDeleteMailIdSet = new ConcurrentHashMap<>();

    /**
     * 添加一份全服邮件
     */
    public boolean addBroadcastMail(Integer contentType, Object[] args, String attachmentContentFormat, Long sendingTime) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            builder.append(args[i]);
            if (i != args.length - 1) {
                builder.append(";");
            }
        }
        AdvancedMail advancedMail = AdvancedMail.Builder.newBroadcastInfo(IdUtil.getNextId(), contentType, builder.toString(),
                attachmentContentFormat, sendingTime)
                .build();
        boolean isSuccess = BroadcastMailInfoDao.getInstance().insertBroadcastMailInfo(advancedMail);
        if (isSuccess) {
            // 1. 将全服邮件信息存储到 local cache 中
            broadcastMailInfo2LocalCache();
            // 2.1 如果玩家在线(user local cache 存在)，则保存记录，且推送给前端
            onlineUserMailWithIdAscMap.forEach((userId, value) -> {
                advancedMail.setReceiverId(userId);
                advancedMail.setRead(false);
                if (StringUtils.isEmpty(advancedMail.getAttachmentContentFormat())) {
                    advancedMail.setAttachmentState(MailAttachmentStateEnum.NO_ATTACHMENT.getAttachmentState());
                } else {
                    advancedMail.setAttachmentState(MailAttachmentStateEnum.NO_RECEIVED.getAttachmentState());
                }
                if (BroadcastMailRecordDao.getInstance().insertRecord(advancedMail)) {
                    onlineUserMailWithIdAscMap.get(userId).add(advancedMail);
                    // todo {@author lyl} 推送
                }
            });
            // 2.2 如果玩家不在线，则不进行处理 (等他上线再处理)
        }
        return isSuccess;
    }

    public boolean addNormalMail(AdvancedMail advancedMail) {
        return NormalMailRecordDao.getInstance().insertRecord(advancedMail);
    }

    /**
     * 广播邮件信息初始化
     */
    public void broadcastMailInfo2LocalCache() {
        List<BroadcastMailInfoPO> infoPOList = BroadcastMailInfoDao.getInstance().getAllInfoOrderBySendingTime();
        broadcastMailSet.addAll(infoPOList);
    }

    /**
     * 一般是登陆时使用
     * 仅仅对指定用户的邮件缓存进行初始化，不进行任何处理(全局邮件对比，过期处理等)
     *
     * @param expectUserId 期望是在线的用户
     */
    public void targetUserMailRecord2LocalCache(long expectUserId) {
        onlineUserMailWithIdAscMap.computeIfAbsent(expectUserId, k -> {
            // 按照 id 自动升序的 list，但是遍历时被其他线程修改，会导致排除异常，不适用
            //  CopyOnWriteArray 提供快照读功能，即迭代时 modCount 使用的是旧数组的；但是会写时复制，大量单独插入不适用
            // 如果用 treeSet ，性能更好
            List<AdvancedMail> currentUserAdvancedMailList = Collections.synchronizedList(new ArrayList<AdvancedMail>() {
                @Override
                public boolean add(AdvancedMail mail) {
                    int index = Collections.binarySearch(this, mail, (new ComparatorUtil.MailIdAscComparator<>()));
                    if (index < 0) {
                        index = ~index;
                    }
                    super.add(index, mail);
                    return true;
                }
            });
            List<BroadcastMailInfoPO> infoPOList = BroadcastMailInfoDao.getInstance().getAllInfo();
            List<BroadcastMailRecordPO> recordPOList = BroadcastMailRecordDao.getInstance().getRecordByReceiverId(expectUserId);
            // 开始连表，拼接 mail 记录
            BroadcastMailInfoPO searchItem;
            AdvancedMail advancedMail;
            for (BroadcastMailRecordPO recordPO : recordPOList) {
                searchItem = new BroadcastMailInfoPO();
                searchItem.setId(recordPO.getBroadcastMailId());
                // 找到当前 record 匹配的 broadcastMail
                int index = Collections.binarySearch(infoPOList, searchItem, new ComparatorUtil.BroadcastMailInfoPOIdAscComparator<>());
                if (index >= 0) {
                    BroadcastMailInfoPO infoPO = infoPOList.get(index);
                    advancedMail = AdvancedMail.Builder.newBroadcastRecord(infoPO.getContentType(), recordPO.getRead(), recordPO.getAttachmentState())
                            .withContentArgs(infoPO.getContentArgs())
                            .withSendingTime(infoPO.getSendingTime())
                            .withId(infoPO.getId())
                            .withSenderId((long) MailTypeEnum.BROAD_CAST.getMailType())
                            .withReceiverId(recordPO.getReceiverId())
                            .withAttachmentContentFormat(infoPO.getAttachmentContentFormat())
                            .withMailType(MailTypeEnum.BROAD_CAST.getMailType())
                            .withDeleted(recordPO.getDeleted())
                            // .withContent()
                            // .withSubject()
                            .withSenderName(MailSenderNameEnum.BROAD_CAST.getSenderName())
                            .build();
                    currentUserAdvancedMailList.add(advancedMail);
                }
            }
            List<NormalMailRecordPO> normalRecordList = NormalMailRecordDao.getInstance().getRecordByUserId(expectUserId, false);


            for (NormalMailRecordPO recordPO : normalRecordList) {
                advancedMail = AdvancedMail.Builder.newInstance(recordPO.getId(), recordPO.getSenderId(), recordPO.getReceiverId(),
                        recordPO.getMailType(), recordPO.getAttachmentState(), recordPO.getAttachmentContentFormat(),
                        recordPO.getRead(), recordPO.getDeleted(), recordPO.getSendingTime())
                        .withContent(recordPO.getContent())
                        .withContentType(recordPO.getContentType())
                        .withContentArgs(recordPO.getContentArgs())
                        .build();
                if (MailTypeEnum.SYSTEM.getMailType().equals(recordPO.getMailType())) {
                    advancedMail.setSenderName(MailSenderNameEnum.SYSTEM.getSenderName());
                } else if (MailTypeEnum.FRIEND.getMailType().equals(recordPO.getMailType())) {
                    // todo check for user，比如 userDao.findUser(receiverId).getName
                } else {
                    advancedMail.setSenderName(MailSenderNameEnum.UNKNOWN.getSenderName());
                }
                currentUserAdvancedMailList.add(advancedMail);
            }

            return currentUserAdvancedMailList;
        });
    }

    /**
     * 失效非在线用户的邮件缓存
     */
    public void removeOnlineUserCache(long userId) {
        onlineUserMailWithIdAscMap.remove(userId);
    }

    /**
     * 更新本地缓存中邮件附件状态, 不需要及时更新 db
     */
    public void updateOnlineUserMailCacheAttachmentState(long userId, long mailId, MailAttachmentStateEnum stateEnum) {
        onlineUserMailWithIdAscMap.computeIfPresent(userId, (k, v) -> {
            AdvancedMail temp = new AdvancedMail();
            temp.setId(mailId);
            int index = Collections.binarySearch(v, temp, new ComparatorUtil.MailIdAscComparator<>());
            if (index >= 0) {
                onlineUserUpdateMailIdSetMap.putIfAbsent(userId, Collections.synchronizedSet(new HashSet<>()));
                onlineUserUpdateMailIdSetMap.get(userId).add(v.get(index).getId());
                v.get(index).setAttachmentState(stateEnum.getAttachmentState());
            }
            return v;
        });
    }

    /**
     * 删除本地缓存中的邮件，不需要及时更新 db
     */
    public void deleteOnlineUserMailInCache(long userId, long mailId) {
        onlineUserMailWithIdAscMap.computeIfPresent(userId, (k, v) -> {
            AdvancedMail temp = new AdvancedMail();
            temp.setId(mailId);
            int index = Collections.binarySearch(v, temp, new ComparatorUtil.MailIdAscComparator<>());
            if (index >= 0) {
                onlineUserDeleteMailIdSet.putIfAbsent(userId, Collections.synchronizedSet(new HashSet<>()));
                onlineUserDeleteMailIdSet.get(userId).add(v.get(index).getId());

                // 广播类型，直接内存中设置即可
                if (MailTypeEnum.BROAD_CAST.getMailType().equals(v.get(index).getMailType())) {
                    temp = v.get(index);
                    temp.setDeleted(true);
                } else {
                    v.remove(index);
                }
            }
            return v;
        });
    }

    /**
     * 根据 userId，将对应的邮件缓存刷新到 db 上去，即快照写
     *
     * @param userId
     */
    public void flush2DbByUserId(long userId) {
        onlineUserMailWithIdAscMap.computeIfPresent(userId, (k, v) -> {
            Integer broadcastMailType = MailTypeEnum.BROAD_CAST.getMailType();
            Integer systemMailType = MailTypeEnum.SYSTEM.getMailType();
            Integer friendsMailType = MailTypeEnum.FRIEND.getMailType();

            List<AdvancedMail> updateBroadcastAdvancedMailRecordList = new ArrayList<>();
            List<AdvancedMail> updateSystemAdvancedMailRecordList = new ArrayList<>();
            List<AdvancedMail> updateFriendsAdvancedMailRecordList = new ArrayList<>();
            List<AdvancedMail> deleteBroadcastAdvancedMailRecordList = new ArrayList<>();
            List<AdvancedMail> deleteSystemAdvancedMailRecordList = new ArrayList<>();
            List<AdvancedMail> deleteFriendsAdvancedMailRecordList = new ArrayList<>();


            AdvancedMail temp = new AdvancedMail();
            AdvancedMail targetAdvancedMail;

            // 处理更新邮件
            Set<Long> updateMailList = onlineUserUpdateMailIdSetMap.get(userId);
            if (!CollectionUtils.isEmpty(updateMailList)) {
                for (Long mailId : updateMailList) {
                    temp.setId(mailId);
                    int index = Collections.binarySearch(v, temp, new ComparatorUtil.MailIdAscComparator<>());
                    if (index >= 0) {
                        targetAdvancedMail = v.get(index);
                        if (broadcastMailType.equals(targetAdvancedMail.getMailType())) {
                            updateBroadcastAdvancedMailRecordList.add(targetAdvancedMail);
                        } else if (systemMailType.equals(targetAdvancedMail.getMailType())) {
                            updateSystemAdvancedMailRecordList.add(targetAdvancedMail);
                        } else if (friendsMailType.equals(targetAdvancedMail.getMailType())) {
                            updateFriendsAdvancedMailRecordList.add(targetAdvancedMail);
                        } else {
                            System.out.println("这啥类型啊: " + targetAdvancedMail.getMailType() + "这邮件有问题啊: " + targetAdvancedMail);
                        }
                    }
                }
            }

            // 处理删除邮件
            Set<Long> deleteMailList = onlineUserDeleteMailIdSet.get(userId);
            if (!CollectionUtils.isEmpty(deleteMailList)) {
                for (Long mailId : deleteMailList) {
                    temp.setId(mailId);
                    int index = Collections.binarySearch(v, temp, new ComparatorUtil.MailIdAscComparator<>());
                    if (index >= 0) {
                        targetAdvancedMail = v.get(index);
                        if (broadcastMailType.equals(targetAdvancedMail.getMailType())) {
                            deleteBroadcastAdvancedMailRecordList.add(targetAdvancedMail);
                        } else if (systemMailType.equals(targetAdvancedMail.getMailType())) {
                            deleteSystemAdvancedMailRecordList.add(targetAdvancedMail);
                        } else if (friendsMailType.equals(targetAdvancedMail.getMailType())) {
                            deleteFriendsAdvancedMailRecordList.add(targetAdvancedMail);
                        } else {
                            System.out.println("这啥类型啊: " + targetAdvancedMail.getMailType() + "这邮件有问题啊: " + targetAdvancedMail);
                        }
                    }
                }
            }

            doUpdate(updateBroadcastAdvancedMailRecordList, updateSystemAdvancedMailRecordList, updateFriendsAdvancedMailRecordList);
            doDelete(deleteBroadcastAdvancedMailRecordList, deleteSystemAdvancedMailRecordList, deleteFriendsAdvancedMailRecordList);
            return v;
        });
    }

    private void doUpdate(List<AdvancedMail> updateBroadcastAdvancedMailRecordList, List<AdvancedMail> updateSystemAdvancedMailRecordList, List<AdvancedMail> updateFriendsAdvancedMailRecordList) {
        BroadcastMailRecordDao.getInstance().updateRecordList(updateBroadcastAdvancedMailRecordList);
    }

    private void doDelete(List<AdvancedMail> deleteBroadcastAdvancedMailRecordList, List<AdvancedMail> deleteSystemAdvancedMailRecordList, List<AdvancedMail> deleteFriendsAdvancedMailRecordList) {
        BroadcastMailRecordDao.getInstance().logicDeleteRecordList(deleteBroadcastAdvancedMailRecordList);
    }


    public Set<BroadcastMailInfoPO> getBroadcastMailSet() {
        return broadcastMailSet;
    }

    public Map<Long, List<AdvancedMail>> getOnlineUserMailWithIdAscMap() {
        return onlineUserMailWithIdAscMap;
    }

    public boolean checkRet(int affectRow) {
        return affectRow >= 0;
    }
}