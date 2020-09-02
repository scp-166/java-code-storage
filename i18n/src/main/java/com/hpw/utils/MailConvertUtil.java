package com.hpw.utils;

import com.hpw.bean.Mail;
import com.hpw.service.MailTemplateCache;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.Objects;

public class MailConvertUtil {
    private MailConvertUtil() {
        throw new RuntimeException(this.getClass().getName() + "cannot init outside");
    }

    /**
     * 有选择的填充 {@code mail} 中的国际化内容 <br>
     * 即 {@code Mail.senderName}, {@code Mail.subject}, {@code Mail.subject} 如果内容不为空，则不会替换
     *
     * @param mail 邮件
     * @param languageType 语言 id
     * @param typeId     类型 id
     * @return 有选择填充后的 {@code mail}
     */
    public static Mail convertI18nMailSelective(Mail mail, int languageType, int typeId) {
        boolean needDefault = checkI18nNeedDefault(languageType, typeId);
        if (!needDefault) {
            if (Objects.isNull(mail.getSubject())) {
                mail.setSubject(MailTemplateCache.getLanguage2TypeTemplateMap().get(languageType).get(typeId).getSubject());
            }
        } else {
            if (Objects.isNull(mail.getSubject())) {
                mail.setSubject(MailTemplateCache.getDefaultTemplate().getSubject());
            }
        }

        if (Objects.isNull(mail.getSenderName())) {
            mail.setSenderName(MailTemplateCache.getSystemNameMap().get(languageType));
        }
        if (Objects.isNull(mail.getContent())) {
            mail.setContent(MailTemplateCache.getLanguage2TypeTemplateMap().get(languageType).get(typeId).getContent());
        }
        return mail;
    }

    /**
     * 强制性的填充 {@code mail} 中的 国际化内容
     *
     * @param mail 邮件
     * @param languageType 语言类型
     * @param contentType 内容类型
     * @return 强制填充后的 {@code mail}
     */
    public static Mail convertI18nMailCompulsive(Mail mail, int languageType, int contentType) {
        boolean needDefault = checkI18nNeedDefault(languageType, contentType);
        if (!needDefault) {
            mail.setSubject(MailTemplateCache.getLanguage2TypeTemplateMap().get(languageType).get(contentType).getSubject());
        } else {
            mail.setSubject(MailTemplateCache.getDefaultTemplate().getSubject());
        }
        mail.setSenderName(MailTemplateCache.getSystemNameMap().get(languageType));
        mail.setContent(MailTemplateCache.getLanguage2TypeTemplateMap().get(languageType).get(contentType).getContent());

        return mail;
    }


    /**
     * 当 languageType 和 typeType 不能找到对应的模板，需要调用方手动处理
     *
     * @param languageType 语言类型
     * @param typeId     邮件内容类型
     * @return 找到对应模板返回 {@code true}
     */
    public static boolean checkI18nNeedDefault(int languageType, int typeId) {
        Map<Integer, MailTemplateCache.TemplateFormatContent> templateContentMap =
                MailTemplateCache.getLanguage2TypeTemplateMap().get(languageType);
        if (CollectionUtils.isEmpty(templateContentMap)) {
            return true;
        }
        MailTemplateCache.TemplateFormatContent templateFormatContent = templateContentMap.get(typeId);
        return Objects.isNull(templateFormatContent);
    }
}
