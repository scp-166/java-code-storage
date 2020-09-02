package com.hpw;


import org.apache.commons.io.IOUtils;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.ByteArrayResource;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Mapper创建工厂
 *
 * @author yellow
 * @version 1.0, 2017/11/3 15:32
 */
public class ComMapperFactory implements AbstractMapperFactory {
    private ComMapperFactory() {
    }

    private static final class Holder {
        private static final ComMapperFactory instance = new ComMapperFactory();
    }

    public static ComMapperFactory getInstance() {
        return Holder.instance;
    }

    GenericXmlApplicationContext comApplicationContext;

    public void init(String configDir) throws Throwable {
        InputStream inputStream = ClassLoader.getSystemResourceAsStream("db/mapper-bean.xml");
        String xml = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        inputStream.close();
        File file = new File(configDir + "/config/db.properties");
        if (file.exists()) {
            xml = xml.replace("classpath:config/db.properties", "file:" + file.getAbsolutePath());
        } else {
            xml = xml.replace("classpath:config/db.properties", String.format("classpath:%s/db.properties", configDir));
        }
        ByteArrayResource resource = new ByteArrayResource(xml.getBytes(StandardCharsets.UTF_8));
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.load(resource);
        ctx.refresh();

        comApplicationContext = ctx;
    }

    public GenericXmlApplicationContext getComApplicationContext() {
        return comApplicationContext;
    }

    public <T> T getMapper(Class<T> clz) {
        return comApplicationContext.getBean(clz);
    }
}

