package com.dazong.common.cache.core.impl;

import com.alibaba.fastjson.serializer.ObjectSerializer;
import groovy.lang.Singleton;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author: DanielLi
 * @Date: 2018/1/10
 * @Description:
 */

@Singleton
public class RedisCacheHandler extends AbstractCacheHandler implements InitializingBean {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    private ObjectSerializer objectSerializer;

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setObjectSerializer(ObjectSerializer objectSerializer) {
        this.objectSerializer = objectSerializer;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
