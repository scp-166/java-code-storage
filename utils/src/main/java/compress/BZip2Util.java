package compress;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.StopWatch;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 效率高, 但是慢
 *
 * @author lyl
 * @date 2020/9/27
 */
public class BZip2Util {

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

    public static byte[] compress(byte[] sourceBytes) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BZip2CompressorOutputStream bcos = new BZip2CompressorOutputStream(out);
        bcos.write(sourceBytes);
        bcos.close();
        return out.toByteArray();
    }

    public static byte[] uncompress(byte[] bytes) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            BZip2CompressorInputStream ungzip = new BZip2CompressorInputStream(
                    in);
            byte[] buffer = new byte[2048];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }
}
