package com.hpw.test;

import com.hpw.ComMapperFactory;
import com.hpw.bean.Mail;
import com.hpw.data.NetMessage;
import com.hpw.data.NetRequest;
import com.hpw.data.NetResponse;
import com.hpw.myenum.MailAttachmentStateEnum;
import com.hpw.myenum.MailSystemNameEnum;
import com.hpw.myenum.MailTypeEnum;
import com.hpw.service.*;
import com.hpw.utils.GameRewardUtil;
import com.hpw.utils.MailConvertUtil;
import com.hpw.utils.MailSenderManager;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MailTest {
    public static void main(String[] args) {
        try {
            ComMapperFactory.getInstance().init("config");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        MailTemplateCache.init(
                new MailSystemNameConfigReader() {
                    @Override
                    public void parse() {
                        // 假装进行了设置
                        MailTemplateCache.addSystemName(MailSystemNameEnum.ENGLISH.getLanguageType(), MailSystemNameEnum.ENGLISH.getSystemName());
                        MailTemplateCache.addSystemName(MailSystemNameEnum.DEFAULT.getLanguageType(), MailSystemNameEnum.DEFAULT.getSystemName());
                    }
                },
                new MailTemplateConfigReader() {
                    @Override
                    public void parse() {
                        // 假装进行了读取
                        MailTemplateCache.TemplateFormatContent templateFormatContent = new MailTemplateCache.TemplateFormatContent();
                        templateFormatContent.setSubject("welcome");
                        templateFormatContent.setContent("deal #<name>, welcome to #<place>， it will pay #<money> yuan ~ have fun !");
                        MailTemplateCache.addLanguage2TypeTemplate(MailSystemNameEnum.ENGLISH.getLanguageType(),
                                MailTypeEnum.WELCOME.getContentType(), templateFormatContent);
                        MailTemplateCache.setDefaultTemplate(templateFormatContent);
                    }
                });


        // test1();
        // test2();
        // test3();
        // test4();

        // testSend();
        // testSend();
        // testSend();
        // testServiceInit();
    }

    public static void testSend() {
        Mail mail = Mail.Builder.newTemplateInstance(0L, 910002L,
                MailTypeEnum.WELCOME.getContentType(),
                false, MailAttachmentStateEnum.NO_ATTACHMENT.getState())
                .withSendingTime(System.currentTimeMillis())
                .withContent("自测")
                .build();
        MailConvertUtil.convertI18nMailCompulsive(mail, 1, mail.getContentType());
        MailSenderManager.getInstance().sendSystemMail(mail, 1, new Object[]{"akarin", "china", 20}, null);
        System.out.println(mail.getContent());
    }


    public static void testServiceInit() {

        Mail mail = Mail.Builder.newTemplateInstance(0L, 910002L,
                MailTypeEnum.WELCOME.getContentType(),
                false, MailAttachmentStateEnum.NO_ATTACHMENT.getState())
                .withContent("自测")
                .build();

        MailConvertUtil.convertI18nMailCompulsive(mail, 1, mail.getContentType());
        MailService service = new MailService();
        NetMessage request = new NetRequest();
        request.setUserId(910002L);
        request.setData("1,1,1,10".getBytes());
        service.init(request);
    }


    public static void test2() {
        NetMessage request = new NetMessage();
        request.setCmd(123);
        request.setUserId(910002L);
        request.setData("1,1,2".getBytes());

        MailService service = new MailService();
        NetResponse init = service.init(request);
        System.out.println(init);

    }

    private static String regex = "(#<[a-zA-Z]+>)";
    private static Pattern pattern;

    public static void test3() {
        format("经过了 #<day> 天，你终于悟到了, 只有 #<money> 元才能让你变得更强", "20", "100");
    }

    public static void format(String content, String... args) {
        pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        Map<Integer, String> formatControlStringMap = new HashMap<>(3);
        int matcher_str_start = 0;
        // 要先查询，不然会抛异常
        // find() 部分匹配即可，即在 content 中部分匹配到 regex 就ok；matches() 要求 content 整个完全 regex
        if (matcher.find()) {
            while (matcher.find(matcher_str_start)) {
                formatControlStringMap.put(matcher.start(), matcher.group(1));
                matcher_str_start = matcher.end();
            }
        }
        System.out.println(formatControlStringMap);

        int count = 0;
        for (Map.Entry<Integer, String> item : formatControlStringMap.entrySet()) {
            content = content.replaceFirst(item.getValue(), args[count]);
            count++;
        }

        System.out.println(content);
    }

    public static void test4() {
        testParam(1, "2", new Date());
    }

    private static void testParam(Object... args) {
        for (Object arg : args) {
            System.out.println(arg.getClass());
            if (arg instanceof Integer) {
                System.out.println("grea");
            }
        }

    }
}
