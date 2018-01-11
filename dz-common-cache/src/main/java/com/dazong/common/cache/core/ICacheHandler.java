package com.dazong.common.cache.core;

import java.util.List;
import java.util.Map;


/**
 * @author: DanielLi
 * @Date: 2018/1/10
 * @Description:缓存操作服务，抽象缓存操作的各种操作场景和提供（可以是redis,memcached,oscached等），此接口只处理存储，不处理并发和锁问题
 */
public interface ICacheHandler {
    public void saveString(final String key,final String str) ;
    public void saveObject(final String key, final Object object) ;
    public void saveMap(final String key,final Map<String, ?> data);
    public void saveMapItem(final String key,final String itemKey,final Object value);
    public void saveList(final String key,final List data);
    public void saveListItem(final String key,final Object object);
    public void delete(final String key) ;
    public void deleteMapItem(final String key, final String itemKey) ;
    public String getString(final String key) ;
    public <T> T getObject(final String key, final Class<T> clazz);
    public <T> T getMapItem(final String key,final String itemKey,final Class<T> type);
    public <T> Map<String, T> getMap(final String key, final Class<T> type);
    public <T>List<T> getList(final String key,final Class<T> type);
}
