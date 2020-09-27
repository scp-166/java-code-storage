package compress;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.xerial.snappy.Snappy;

import java.io.IOException;

/**
 * Snappy压缩
 *
 * @author lyl
 * @date 2020/9/27
 */
public class SnappyUtil {
    public static byte[] compress(byte[] sourceBytes) throws IOException {
        return Snappy.compress(sourceBytes);
    }

    public static byte[] uncompress(byte[] bytes) throws IOException {
        return Snappy.uncompress(bytes);
    }

    public static void main(String[] args) throws Exception {
        String random = RandomStringUtils.randomAlphanumeric(100000000);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        byte[] compress = compress(random.getBytes());
        String s1 = new String(compress);
        System.out.println(s1.length());

        String s = new String(uncompress(compress));

        System.out.println(random.equals(s));

        stopWatch.stop();
        System.out.println(stopWatch.getTime());
    }
}
