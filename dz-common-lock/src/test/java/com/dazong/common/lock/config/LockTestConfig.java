package com.dazong.common.lock.config;

import com.dazong.common.lock.annotation.EnableDistrbutionLock;
import com.dazong.common.lock.service.LockAopService;
import com.dazong.common.lock.zookeeper.ZKClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author Sam
 * @version 1.0.0
 */
@Configuration
@EnableDistrbutionLock
@ComponentScan("com.dazong.common")
public class LockTestConfig {

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName("172.16.21.14");
        jedisConnectionFactory.setPort(6379);
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate redisTemplate(@Autowired JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        return redisTemplate;
    }

    @Bean
    public ZKClient zkClient() {
        ZKClient zkClient = new ZKClient();
        zkClient.setServer("172.16.21.12:2181");
        return zkClient;
    }

    @Bean
    public LockAopService lockAopService() {
        return new LockAopService();
    }


}
