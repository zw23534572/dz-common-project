package com.dazong.common.autoconfig;


import com.dazong.common.cache.core.impl.RedisCacheHandler;
import com.dazong.common.cache.serialize.JdkSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author DanielLi
 * @description Redis Cache自动化配置
 * 方式1:可以获取到redis链接
 * @Configuration
 @ImportResource("/META-INF/dz-common-cache.xml")
 @ConditionalOnClass({AbstractAutoConfigure.class, RedisCacheHandler.class})

 * 方式2:不可以获取到redis链接
 * @ConditionalOnClass({RedisCacheHandler.class})
 * public class RedisAutoConfigure extends AbstractAutoConfigure
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
    public RedisTemplate redisTemplate() {
        RedisTemplate template = new RedisTemplate();
        template.setConnectionFactory(getConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new JdkSerializationRedisSerializer());
        return template;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public JedisConnectionFactory getConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        JedisPoolConfig config = getRedisConfig();
        factory.setPoolConfig(config);
        return factory;
    }


    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public JedisPoolConfig getRedisConfig() {
        return new JedisPoolConfig();
    }
}
