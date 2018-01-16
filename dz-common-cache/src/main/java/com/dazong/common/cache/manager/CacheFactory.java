package com.dazong.common.cache.manager;

import com.dazong.common.cache.constants.CacheType;
import com.dazong.common.cache.core.ICacheHandler;
import com.dazong.common.cache.core.impl.AbstractCacheHandler;
import com.dazong.common.cache.core.impl.LocalCacheHandler;
import com.dazong.common.cache.core.impl.MemcacheHandler;
import com.dazong.common.cache.core.impl.RedisCacheHandler;
import com.dazong.common.cache.exception.CacheException;
import groovy.lang.Singleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Service;

/**
 * @author: DanielLi
 * @Date: 2018/1/10
 * @Description:创建缓存处理框架的工厂，负责维护缓存处理类的生命周期
 */
@Singleton
@Service
public class CacheFactory extends ApplicationObjectSupport {

    @Autowired
    RedisCacheHandler redisCacheHandler;

    @Autowired
    MemcacheHandler memcacheHandler;

    @Autowired
    LocalCacheHandler localCacheHandler;

    ICacheHandler cacheHandler;
    /**
     * 获取缓存处理器
     * @param cacheType 缓存枚举配置
     * @return
     */
    public ICacheHandler getCacheHandler(CacheType cacheType){
        AbstractCacheHandler cacheHandlerTemp = null;
        String msgException = "请选择需要使用的缓存框架";
        switch (cacheType){
            case CACHE_REDIS:
                cacheHandlerTemp = redisCacheHandler;
                break;
            case CACHE_MEMCACHE:
                cacheHandlerTemp = memcacheHandler;
                break;
            case CACHE_LOCALCACHE:
                cacheHandlerTemp = localCacheHandler;
                break;
            default:
                throw new CacheException(msgException);
        }
        return cacheHandlerTemp;
    }

    /**
     * 获取默认缓存处理器
     * @return
     */
    public ICacheHandler getDefaultCacheHandler(){
        String[] arrayBean = getApplicationContext().getBeanNamesForAnnotation(Configuration.class);
        if(arrayBean.length == 0){
            throw new CacheException("请按照使用说明配置项目");
        }
        // 设置默认处理器
        if(null == cacheHandler){
            cacheHandler = redisCacheHandler;
        }
        return cacheHandler;
    }
    public  void setDefaultCacheHandler(ICacheHandler cacheHandler){
        this.cacheHandler = cacheHandler;
    }
}
