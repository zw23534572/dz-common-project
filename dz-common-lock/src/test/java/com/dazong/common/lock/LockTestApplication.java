package com.dazong.common.lock;

import com.dazong.common.lock.annotation.EnableDistrbutionLock;
import com.dazong.common.lock.service.LockDemoService;
import com.dazong.common.lock.zookeeper.ZookeeperClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author Sam
 * @version 1.0.0
 */
@SpringBootApplication
@EnableDistrbutionLock
public class LockTestApplication {
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
    public ZookeeperClient zkClient() {
        ZookeeperClient zookeeperClient = new ZookeeperClient();
        zookeeperClient.setServer("172.16.21.12:2181");
        return zookeeperClient;
    }

    @Bean
    public LockDemoService lockAopService() {
        return new LockDemoService();
    }


    public static void main(String[] args) {
        SpringApplication.run(LockTestApplication.class);
    }

}
