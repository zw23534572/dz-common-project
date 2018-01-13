package com.dazong.common.cache.manager;

import com.dazong.common.cache.constants.CacheType;
import com.dazong.common.cache.core.ICacheHandler;
import com.dazong.common.cache.core.impl.AbstractCacheHandler;
import com.dazong.common.cache.core.impl.LocalCacheHandler;
import com.dazong.common.cache.core.impl.MemcacheHandler;
import com.dazong.common.cache.core.impl.RedisCacheHandler;
import com.dazong.common.cache.exception.CacheException;
import groovy.lang.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: DanielLi
 * @Date: 2018/1/10
 * @Description:创建缓存处理框架的工厂，负责维护缓存处理类的生命周期
 */
@Singleton
@Service
public class CacheFactory extends ApplicationObjectSupport {
    private static final Logger logger = LoggerFactory.getLogger(CacheFactory.class);

    @Autowired
    RedisCacheHandler redisCacheHandler;

    @Autowired
    MemcacheHandler memcacheHandler;

    @Autowired
    LocalCacheHandler localCacheHandler;

    /**
     * 获取缓存处理器
     * @param cacheType 缓存枚举配置
     * @return
     */
    public ICacheHandler getCacheHandler(CacheType cacheType){
        AbstractCacheHandler cacheHandler = null;
        String msgException = "请选择需要使用的缓存框架";
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
                throw new CacheException(msgException);
        }
        return cacheHandler;
    }

    /**
     * 获取默认缓存处理器，根据自动注入缓存配置Bean的顺序来进行选择,由用户自定义
     * 最先注入的缓存bean则为默认缓存处理器
     * @return
     */
    public ICacheHandler getDefaultCacheHandler(){
        String[] arrayBean = getApplicationContext().getBeanNamesForAnnotation(Configuration.class);

        if(arrayBean.length == 0){
            throw new CacheException("请按照使用说明配置项目");
        }
        String beanFirst = arrayBean[0];
        if(beanFirst.equalsIgnoreCase(CacheType.CACHE_REDIS.getTypeDesc())){
            return getCacheHandler(CacheType.CACHE_REDIS);
        }
        if(beanFirst.equalsIgnoreCase(CacheType.CACHE_MEMCACHE.getTypeDesc())){
            return getCacheHandler(CacheType.CACHE_MEMCACHE);
        }
        if(beanFirst.equalsIgnoreCase(CacheType.CACHE_LOCALCACHE.getTypeDesc())){
            return getCacheHandler(CacheType.CACHE_LOCALCACHE);
        }
        String msgException = "暂时不支持你配置的缓存框架";
        throw new CacheException(msgException);
    }
}
