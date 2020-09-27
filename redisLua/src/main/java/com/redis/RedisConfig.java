package com.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    
    /** Redis连接工厂 */
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     *  <h2> String 序列化器 </h2>
     *
     * @return {@link org.springframework.data.redis.core.StringRedisTemplate}
     */
    @Bean
    public StringRedisSerializer stringRedisSerializer() {
        return new StringRedisSerializer();
    }

    /**
     * 根据条件创建一个 RedisTemplate
     *
     * @param stringRedisSerializer       string 序列化器
     * @param redisSerializer             json 序列化器
     * @param isEnableTransactionSupport  是否开启事务
     * @return {@link org.springframework.data.redis.core.RedisTemplate}
     */
    private RedisTemplate<String, Object> createRedisTemplate(StringRedisSerializer stringRedisSerializer,
                                                              RedisSerializer redisSerializer,
                                                              boolean... isEnableTransactionSupport) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        ////////////////////////////////////////////////////
        //      默认使用的是 JdkSerializationRedisSerializer
        ////////////////////////////////////////////////////
        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用 redisSerializer
        template.setValueSerializer(redisSerializer);
        // hash的value序列化方式采用 redisSerializer
        template.setHashValueSerializer(redisSerializer);

        // 是否开启事务
        if (isEnableTransactionSupport.length == 0) {
            isEnableTransactionSupport = new boolean[]{false};
        }
        template.setEnableTransactionSupport(isEnableTransactionSupport[0]);

        template.afterPropertiesSet();
        return template;
    }



    /**
     * <h2> 默认用的 jackson 序列化模板 </h2>
     *
     * @return {@link org.springframework.data.redis.core.RedisTemplate}
     */
    @Bean
    public RedisTemplate<String, Object> jacksonRedisTemplate() {
        return createRedisTemplate(stringRedisSerializer(), new GenericJackson2JsonRedisSerializer());
    }
}