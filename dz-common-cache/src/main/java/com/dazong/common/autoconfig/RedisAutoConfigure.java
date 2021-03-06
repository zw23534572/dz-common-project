package com.dazong.common.autoconfig;


import com.dazong.common.cache.core.impl.RedisCacheHandler;
import com.dazong.common.serialize.JdkSerializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author DanielLi
 * @description Redis Cache自动化配置
 **/
@Configuration
@ImportResource("classpath:/META-INF/dz-common-cache.xml")
@ConditionalOnClass({RedisCacheHandler.class})
public class RedisAutoConfigure {


    @Bean
    @ConditionalOnMissingBean({RedisCacheHandler.class})
    public RedisCacheHandler redisCacheHandler(@Autowired RedisTemplate redisTemplate) {
        RedisCacheHandler cacheHandler = new RedisCacheHandler();
        cacheHandler.setObjectSerializer(new JdkSerializer());
        cacheHandler.setRedisTemplate(redisTemplate);
        return cacheHandler;
    }


    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate template = new RedisTemplate();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new JdkSerializationRedisSerializer());
        return template;
    }
}
