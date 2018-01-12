package com.dazong.common.autoconfig;


import com.dazong.common.cache.core.impl.RedisCacheHandler;
import com.dazong.common.cache.manager.CacheFactory;
import com.dazong.common.cache.serialize.FstObjectSerializer;
import com.dazong.common.cache.serialize.FstRedisSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author DanielLi
 * @description Redis Cache自动化配置
 */
public class RedisAutoConfigure extends AbstractAutoConfigure{

    @Value("${spring.redis.host}")
    private String hostName;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.port}")
    private Integer port;
    @Value("${spring.redis.database}")
    private int database;

    @Bean
    @ConditionalOnMissingBean({CacheFactory.class})
    public RedisCacheHandler cacheHandler(@Autowired RedisTemplate redisTemplate) {
        RedisCacheHandler cacheHandler = new RedisCacheHandler();
        cacheHandler.setObjectSerializer(new FstObjectSerializer());
        cacheHandler.setRedisTemplate(redisTemplate);
        return cacheHandler;
    }


    @Bean
    public RedisTemplate redisTemplate() {
        RedisTemplate template = new RedisTemplate();
        template.setConnectionFactory(getConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new FstRedisSerializer());
        return template;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public JedisConnectionFactory getConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(hostName);
        factory.setPassword(password);
        factory.setPort(port);
        factory.setDatabase(database);
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
