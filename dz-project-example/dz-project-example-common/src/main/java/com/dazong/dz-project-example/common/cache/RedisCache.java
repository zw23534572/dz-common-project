package com.dazong.dz-project-example.common.cache;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author huqichao
 */
@Component
@SuppressWarnings("unchecked")
public class RedisCache {

    private Logger logger = LoggerFactory.getLogger(RedisCache.class);

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (!keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }


    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        return set(key, value, null);
    }

    /**
     *
     * @Title: set
     * @Description: 写入缓存带有效期
     * @param  key
     * @param  value
     * @param  expireTime
     * @return boolean    返回类型
     * @throws
     */
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            if(expireTime!=null){
                redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            }
            result = true;
        } catch (Exception e) {
            logger.error("set", e);
        }
        return result;
    }


    /**
     * 写入缓存 （json方式存储）
     *
     * @param key
     * @param value
     * @return
     */
    public boolean setAsJson(final String key, Object value) {
        return setAsJson(key, value, null);
    }

    /**
     * 写入缓存（json方式存储）
     *
     * @param key
     * @param value
     * @return
     */
    public boolean setAsJson(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, JSON.toJSONString(value));
            if(expireTime!=null){
                redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            }
            result = true;
        } catch (Exception e) {
            logger.error("setAsJson", e);
        }
        return result;
    }

    /**
     * 写入list 永久缓存
     * @param key
     * @param value
     * @return
     */
    public Long pushList(final String key, Object value){
        Long res = (long) 0;
        try {
            res = redisTemplate.opsForList().leftPush(key, value);
        } catch (Exception e) {
            logger.error("pushList", e);
        }
        return res;
    }

    /**
     * 入栈 （redis写入格式为list）
     *
     * @param key
     * @param value
     * @return
     */
    public Long pushList(final String key, Object value, Long expireTime) {
        Long res = (long) 0;
        try {
            res = redisTemplate.opsForList().leftPush(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("pushList", e);
        }
        return res;
    }

    public Object getAndSet(String key, Object value) {
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        return operations.getAndSet(key, value);
    }
}
