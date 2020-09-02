package com.hpw.dao;

import com.hpw.ComMapperFactory;
import com.hpw.bean.DbPage;
import com.hpw.bean.SystemMailRecordBO;
import com.hpw.bean.SystemMailRecordPO;
import com.hpw.dao.mapper.SystemMailRecordPOMapper;
import com.hpw.service.MailFormatUtil;
import com.hpw.utils.ConvertObjUtil;
import com.hpw.utils.MailConvertUtil;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class SystemMailRecordDao {
    private static class Holder {
        public static SystemMailRecordDao instance = new SystemMailRecordDao();
    }

    public static SystemMailRecordDao getInstance() {
        return Holder.instance;
    }

    private SystemMailRecordPOMapper mapper = ComMapperFactory.getInstance().getMapper(SystemMailRecordPOMapper.class);

    /**
     * 获得 userId 对应的所有完整邮件
     * <strong>不填充邮件内容，需要调用方自行填充</strong>
     *
     * @param userId   用户id
     * @param isSender 是否发送方
     * @return SystemMailRecord 业务类
     */
    public SystemMailRecordBO getAllRecordByUserId(long userId, boolean isSender) {
        SystemMailRecordPO po = mapper.selectByUserId(userId, isSender);
        return ConvertObjUtil.toSystemMailRecordBO(po);
    }

    /**
     * 获得 userId 对应的所有完整邮件
     * <strong>会填充邮件内容，但是不进行格式转换，需要调用方自行转换格式字符串</strong>
     *
     * @param userId   用户id
     * @param isSender 是否发送方
     * @param languageType 语言类型
     * @return SystemMailRecord 业务类
     */
    public SystemMailRecordBO getAllRecordByUserId(long userId, boolean isSender, int languageType) {
        SystemMailRecordPO po = mapper.selectByUserId(userId, isSender);
        SystemMailRecordBO bo = ConvertObjUtil.toSystemMailRecordBO(po);
        if (Objects.nonNull(bo)) {
            MailConvertUtil.convertI18nMailCompulsive(bo.getMail(), languageType, bo.getMail().getContentType());
        }
        return bo;
    }


    /**
     * SystemMailRecord bo 转 po
     *
     * @param recordBO SystemMailRecord 业务类
     * @return 是否插入 db 成功
     */
    public boolean insertFullRecord(SystemMailRecordBO recordBO) {
        SystemMailRecordPO recordPO = ConvertObjUtil.toSystemMailRecordPO(recordBO);
        if (Objects.isNull(recordPO)) {
            return false;
        }
        int ret = mapper.insertFullRecord(recordPO);
        return checkRet(ret);
    }

    /**
     * 获得 userId 对应的邮件数量
     *
     * @param userId   用户 id
     * @param isSender 是否发送方
     * @return
     */
    public int getAllRecordCountById(long userId, boolean isSender) {
        return mapper.getAllRecordCountById(userId, isSender);
    }

    /**
     * 分页
     *
     * @param userId             发送者 id
     * @param isSender           是否要搜索发送方
     * @param expectPageRowSize  期望一页的大小
     * @param pageStart          第几页开始
     * @param languageTypeIfNeed 如果需要转换 mail 的 content，则传入该值
     * @return
     */
    public DbPage<SystemMailRecordBO> getRecordPage(long userId, boolean isSender,
                                                    int pageStart, int expectPageRowSize,
                                                    Integer languageTypeIfNeed) {
        if (pageStart == 0) {
            return null;
        }

        int rowSize = mapper.getAllRecordCountById(userId, isSender);
        int limitStart = (pageStart - 1) * expectPageRowSize;
        List<SystemMailRecordPO> recordPOList = mapper.getRecordLimit(userId, isSender, limitStart, expectPageRowSize);
        if (CollectionUtils.isEmpty(recordPOList)) {
            return null;
        }

        DbPage<SystemMailRecordBO> page = new DbPage<>();
        page.setTotalPageItemCount(rowSize);
        page.setTotalPageSize((int) Math.ceil(1.0 * rowSize / expectPageRowSize));
        page.setCurrentPageNum(pageStart);
        page.setCurrentPageItemCount(expectPageRowSize);

        List<SystemMailRecordBO> recordBOList = new ArrayList<>(recordPOList.size());
        SystemMailRecordBO tempBO;
        for (SystemMailRecordPO po : recordPOList) {
            tempBO = ConvertObjUtil.toSystemMailRecordBO(po);
            if (Objects.nonNull(languageTypeIfNeed) && Objects.nonNull(tempBO) && Objects.nonNull(tempBO.getMail())) {
                MailConvertUtil.convertI18nMailCompulsive(tempBO.getMail(),
                        languageTypeIfNeed, tempBO.getMail().getContentType());
                // 如果有格式化参数，则进行格式化
                if (Objects.nonNull(tempBO.getMail().getContentArgs())) {
                    tempBO.getMail().setContent
                            (MailFormatUtil.format(tempBO.getMail().getContent(), tempBO.getMail().getContentArgs()));
                }
            }
            recordBOList.add(tempBO);

        }
        page.setCurrentPageRecordList(recordBOList);
        return page;
    }

    /**
     * 分页, 不提供实际的 rewardList
     *
     * @param userId             发送者 id
     * @param isSender           是否要搜索发送方
     * @param expectPageRowSize  期望一页的大小
     * @param pageStart          第几页开始
     * @param languageTypeIfNeed 如果需要转换 mail 的内容，则传入该值
     * @return
     */
    public DbPage<SystemMailRecordBO> getRecordPageWithRewardList(long userId, boolean isSender,
                                                                  int pageStart, int expectPageRowSize,
                                                                  Integer languageTypeIfNeed) {
        if (pageStart == 0) {
            return null;
        }

        int rowSize = mapper.getAllRecordCountById(userId, isSender);
        int limitStart = (pageStart - 1) * expectPageRowSize;
        List<SystemMailRecordPO> recordPOList = mapper.getRecordLimit(userId, isSender, limitStart, expectPageRowSize);
        if (CollectionUtils.isEmpty(recordPOList)) {
            return null;
        }

        DbPage<SystemMailRecordBO> page = new DbPage<>();
        page.setTotalPageItemCount(rowSize);
        page.setTotalPageSize((int) Math.ceil(1.0 * rowSize / expectPageRowSize));
        page.setCurrentPageNum(pageStart);
        page.setCurrentPageItemCount(expectPageRowSize);

        List<SystemMailRecordBO> recordBOList = new ArrayList<>(recordPOList.size());
        SystemMailRecordBO tempBO;
        for (SystemMailRecordPO po : recordPOList) {
            tempBO = ConvertObjUtil.toSystemMailRecordBOWithoutGameRewardList(po);
            if (Objects.nonNull(languageTypeIfNeed) && Objects.nonNull(tempBO) && Objects.nonNull(tempBO.getMail())) {
                MailConvertUtil.convertI18nMailCompulsive(tempBO.getMail(),
                        languageTypeIfNeed, tempBO.getMail().getContentType());
                // 如果有格式化参数，则进行格式化
                if (Objects.nonNull(tempBO.getMail().getContentArgs())) {
                    tempBO.getMail().setContent
                            (MailFormatUtil.format(tempBO.getMail().getContent(), tempBO.getMail().getContentArgs()));
                }
            }
            recordBOList.add(tempBO);
        }
        page.setCurrentPageRecordList(recordBOList);
        return page;
    }


    public boolean deleteTargetMail() {
        return false;
    }

    public boolean checkRet(int affectRow) {
        return affectRow >= 0;
    }
}