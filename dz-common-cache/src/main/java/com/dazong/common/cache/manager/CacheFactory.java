package com.dazong.common.cache.manager;

import com.dazong.common.cache.constants.CacheType;
import com.dazong.common.cache.core.*;
import com.dazong.common.cache.core.impl.AbstractCacheHandler;
import com.dazong.common.cache.core.impl.LocalCacheHandler;
import com.dazong.common.cache.core.impl.MemcacheHandler;
import com.dazong.common.cache.core.impl.RedisCacheHandler;
import com.dazong.common.cache.exception.CacheException;
import groovy.lang.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: DanielLi
 * @Date: 2018/1/10
 * @Description:创建缓存处理框架的工厂，负责维护缓存处理类的生命周期
 */
@Singleton
@Service
public class CacheFactory {
    private static final Logger logger = LoggerFactory.getLogger(CacheFactory.class);

    @Autowired
    RedisCacheHandler redisCacheHandler;

    @Autowired
    MemcacheHandler memcacheHandler;

    @Autowired
    LocalCacheHandler localCacheHandler;

    public ICacheHandler getCacheHandler(CacheType cacheType){
        AbstractCacheHandler cacheHandler = null;
        switch (cacheType){
            case CACHE_REDIS:
                cacheHandler = redisCacheHandler;
                break;
            case CACHE_MEMCACHE:
                cacheHandler = memcacheHandler;
                break;
            case CACHE_LOCALCACHE:
                cacheHandler = localCacheHandler;
                break;
            default:
                throw new CacheException("请选择需要使用的缓存框架");
        }
        return cacheHandler;
    }

    /**
     * 获取默认缓存处理器，目前是Redis，可以根据
     * @return
     */
    public ICacheHandler getDefaultCacheHandler(){
        return getCacheHandler(CacheType.CACHE_REDIS);
    }
}
