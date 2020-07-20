import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 邮件发送
 */
public class MailUtil {
    private static Timer timer;
    private static LinkedBlockingDeque<Mail> mailQueue = new LinkedBlockingDeque<>(2000);


    private static class Mail {
        /**
         * 邮件服务提供商的地址，有 stmp pop3 等
         */
        private String mailServerHost;
        /**
         * 登陆用户
         */
        private String userAccount;
        /**
         * 一般是授权码
         */
        private String password;

        private String fromMailAddress;
        private List<String> toMailList;
        private String mailTitle;
        private String mailContent;

        public void setMailInfo(String mailTitle, String mailContent) {
            this.mailTitle = mailTitle;
            this.mailContent = mailContent;
        }


        public String getMailContent() {
            return this.mailContent;
        }

        public void setFromMailAddress(String fromMailAddress) {
            this.fromMailAddress = fromMailAddress;
        }

        public void addToMailAddress(String toMailAddress) {
            if (Objects.isNull(toMailList)) {
                synchronized (this) {
                    if (Objects.isNull(toMailList)) {
                        toMailList = new ArrayList<>(1);
                    }
                }
            }
            this.toMailList.add(toMailAddress);
        }

        public void setMailConfig(String mailServerHost, String userAccount, String password) {
            this.mailServerHost = mailServerHost;
            this.userAccount = userAccount;
            this.password = password;
        }

        public boolean checkMail() {
            if (Objects.isNull(mailServerHost)) {
                return false;
            }
            if (Objects.isNull(userAccount)) {
                return false;
            }
            if (Objects.isNull(password)) {
                return false;
            }
            if (Objects.isNull(fromMailAddress)) {
                return false;
            }
            if (Objects.isNull(toMailList) || toMailList.isEmpty()) {
                return false;
            }
            if (Objects.isNull(mailTitle)) {
                return false;
            }
            if (Objects.isNull(mailContent)) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return "Mail{" +
                    "mailServerHost='" + mailServerHost + '\'' +
                    ", userAccount='" + userAccount + '\'' +
                    ", password='" + password + '\'' +
                    ", fromMailAddress='" + fromMailAddress + '\'' +
                    ", toMailList=" + toMailList +
                    ", mailTitle='" + mailTitle + '\'' +
                    ", mailContent='" + mailContent + '\'' +
                    '}';
        }
    }


    private static boolean send(Mail mail) {
        Properties props = new Properties();
        //存储发送邮件服务器的信息
        props.put("mail.smtp.host", mail.mailServerHost);
        //同时通过验证
        props.put("mail.smtp.auth", "true");
        props.put("mail.transport.protocol", "smtp");

        // getInstance 获得非共享的 session
        // getDefaultInstance 获得共享的 session
        Session session = Session.getInstance(props);
        session.setDebug(true);

        // 由邮件回话新建一个消息对象
        MimeMessage message = new MimeMessage(session);
        try {
            // 1.设置邮件信息
            // 1.1 设置发件人
            message.setFrom(new InternetAddress(mail.fromMailAddress));
            InternetAddress[] toMailAddressArr = new InternetAddress[mail.toMailList.size()];
            for (int i = 0; i < toMailAddressArr.length; i++) {
                toMailAddressArr[i] = new InternetAddress(mail.toMailList.get(i));
            }
            // 1.2 TO: 收件人 CC: 抄送
            message.setRecipients(Message.RecipientType.TO, toMailAddressArr);
            // 1.3 设置标题
            message.setSubject(mail.mailTitle);
            // 1.4 设置内容
            message.setContent(mail.mailContent, "text/html;charset=gbk");
            // 1.5 设置发信时间
            message.setSentDate(new Date());
            // 1.6 存储邮件信息
            message.saveChanges();

            // 2 发送邮件
            Transport transport = session.getTransport();
            transport.connect(mail.userAccount, mail.password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

        } catch (MessagingException e) {
            // todo {@author lyl} need log
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void put(Mail mail) {
        if (!mail.checkMail()) {
            System.out.println(String.format("mail check fail: %s", mail.toString()));
            return;
        }

        try {
            mailQueue.put(mail);
        } catch (InterruptedException e) {
            // todo {@author lyl} log
            e.printStackTrace();
        }
        if (Objects.isNull(timer)) {
            synchronized (MailUtil.class) {
                if (Objects.isNull(timer)) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (mailQueue.size() > 0) {
                                Mail mail = mailQueue.poll();
                                if (Objects.nonNull(mail)) {
                                    boolean send = send(mail);
                                    if (!send) {
                                        // todo {@author lyl} log that send fail
                                        System.out.println(String.format("send Mail fail: %s", mail.toString()));
                                    }
                                }
                            }
                        }
                    }, 1000, 1000);
                }
            }
        }
    }

    public static void main(String[] args) {
        new Thread(() -> {
            for (int i = 0; i < 2; i++) {
                MailUtil.Mail mail = new MailUtil.Mail();
                mail.setMailConfig("smtp.163.com", System.getenv("MY_MAIL"), System.getenv("MAIL_AUTHORIZATION_CODE"));
                mail.setFromMailAddress(System.getenv("MY_MAIL"));
                mail.setMailInfo("title" + i, "content");
                mail.addToMailAddress(System.getenv("MY_MAIL"));
                MailUtil.put(mail);

            }
        }).start();

        try {

            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
