package com.hpw.test;

import com.hpw.ComMapperFactory;
import com.hpw.bean.AdvancedMail;
import com.hpw.dao.MailDao;
import com.hpw.data.NetMessage;
import com.hpw.myenum.MailAttachmentStateEnum;
import com.hpw.myenum.MailTypeEnum;
import com.hpw.service.MailService;
import com.hpw.utils.IdUtil;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MainTest {
    public static void main(String[] args) {
        Object dataSource = init();
        // testAddInfo();
        // testBroadcast();
        testLogin();
        // testDelete()


        // insertInfo(dataSource);


    }

    public static Object init() {
        try {
            ComMapperFactory.getInstance().init("config");
            GenericXmlApplicationContext comApplicationContext = ComMapperFactory.getInstance().getComApplicationContext();
           return comApplicationContext.getBean("hallDataSource");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    public static void insertInfo(Object dataSource) {
        int size = 1 * 100000;
        // 512 with null  10w-3.52mb
        String sql = "insert into test0 (age) values(?)";
        // 255 with null  10w-3.52mb
        // 255 String sql = "insert into test1 (age) values(?)";
        // with empty  10w-2.52mb
        // String sql = "insert into test2 (age) values(?)";
        // no column
        // String sql = "insert into test3 (age) values(?)";
        JdbcTemplate jdbcTemplate = new JdbcTemplate((DataSource) dataSource);


        List<Object[]> batchArgs = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            batchArgs.add(new Object[]{ThreadLocalRandom.current().nextInt()});
        }

        int[] ints = jdbcTemplate.batchUpdate(sql, batchArgs);
        System.out.println(Arrays.toString(ints));

    }

    public static void testAddInfo() {
        MailDao.getInstance().addBroadcastMail(9, new Object[]{8,2,"hasdaeheda"}, null, System.currentTimeMillis());
        AdvancedMail advancedMail = AdvancedMail.Builder.newInstance(IdUtil.getNextId(), 1L, 910002L, MailTypeEnum.FRIEND.getMailType(),
                MailAttachmentStateEnum.NO_RECEIVED.getAttachmentState(), null,
                false,
                false,
                System.currentTimeMillis()
        )
                .withContent("哈哈哈哈哈哈没想到吧")
                .build();
        MailDao.getInstance().addNormalMail(advancedMail);
    }

    public static void testBroadcast() {
        MailDao.getInstance().broadcastMailInfo2LocalCache();
        MailDao.getInstance().targetUserMailRecord2LocalCache(910002L);
        System.out.println(MailDao.getInstance().getBroadcastMailSet());
        System.out.println(MailDao.getInstance().getOnlineUserMailWithIdAscMap());

        // 假设在添加全局邮件前，用户退出游戏
        MailDao.getInstance().removeOnlineUserCache(910002L);
        MailDao.getInstance().addBroadcastMail(9, new Object[]{8,2,"heheda"}, null, System.currentTimeMillis());

        System.out.println(MailDao.getInstance().getBroadcastMailSet());
        System.out.println(MailDao.getInstance().getOnlineUserMailWithIdAscMap());
        List<AdvancedMail> advancedMails = MailDao.getInstance().getOnlineUserMailWithIdAscMap().get(910002L);
        for (AdvancedMail advancedMail1 : advancedMails) {
            System.out.println(advancedMail1.getId());
        }
    }

    public static void testLogin() {
        NetMessage request = new NetMessage();
        request.setUserId(910002L);
        MailService service = new MailService();
        service.login(request);
    }

    public static void testDelete() {
        MailDao.getInstance().broadcastMailInfo2LocalCache();
        MailDao.getInstance().targetUserMailRecord2LocalCache(910002L);

        System.out.println(MailDao.getInstance().getBroadcastMailSet());
        System.out.println(MailDao.getInstance().getOnlineUserMailWithIdAscMap());

        System.out.println("=======================================================");

        // MailDao.getInstance().deleteOnlineUserMailInCache(910002L, 485539183629176832L);
        MailDao.getInstance().updateOnlineUserMailCacheAttachmentState(910002L, 485539183629176832L, MailAttachmentStateEnum.ALREADY_RECEIVED);

        System.out.println(MailDao.getInstance().getBroadcastMailSet());
        System.out.println(MailDao.getInstance().getOnlineUserMailWithIdAscMap());

        System.out.println("=======================================================");

        MailDao.getInstance().flush2DbByUserId(910002L);

        System.out.println("=======================================================");

        System.out.println(MailDao.getInstance().getBroadcastMailSet());
        System.out.println(MailDao.getInstance().getOnlineUserMailWithIdAscMap());
    }
}
