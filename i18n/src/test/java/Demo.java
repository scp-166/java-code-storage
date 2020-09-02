import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 国际化简单测试
 */
public class Demo {
    public static void main(String[] args) {

        Locale th = new Locale("th", "");
        ResourceBundle mailInfo = ResourceBundle.getBundle("mailInfo", th);
        System.out.println(mailInfo.getLocale());
        System.out.println(mailInfo.getString("welcome"));
    }

}
