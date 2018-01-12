package com.dazong.common.cache.core;

import java.util.List;
import java.util.Map;


/**
 * @author: DanielLi
 * @Date: 2018/1/10
 * @Description:缓存操作服务，抽象缓存操作的各种操作场景和提供（可以是redis,memcached,oscached等），此接口只处理存储，不处理并发和锁问题
 */
public interface ICacheHandler {
    /**
     * 保存字符串
     * @param key
     * @param str
     */
    public void saveString(final String key,final String str) ;

    /**
     * 保存对象
     * @param key
     * @param object
     */
    public void saveObject(final String key, final Object object) ;

    /**
     * 保存Map对象
     * @param key
     * @param data
     */
    public void saveMap(final String key,final Map<String, ?> data);

    /**
     * 保存Map对象的某个key
     * @param key
     * @param itemKey
     * @param value
     */
    public void saveMapItem(final String key,final String itemKey,final Object value);

    /**
     * 保存List
     * @param key
     * @param data
     */
    public void saveList(final String key,final List data);

    /**
     * 保存List
     * @param key
     * @param object
     */
    public void saveListItem(final String key,final Object object);

    /**
     * 删除key
     * @param key
     */
    public void delete(final String key) ;

    /**
     * 删除key
     * @param key
     * @param itemKey
     */
    public void deleteMapItem(final String key, final String itemKey) ;

    /**
     * 获取key
     * @param key
     * @return
     */
    public String getString(final String key) ;

    /**
     * 获取key
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getObject(final String key, final Class<T> clazz);

    /**
     * 获取key
     * @param key
     * @param itemKey
     * @param type
     * @param <T>
     * @return
     */
    public <T> T getMapItem(final String key,final String itemKey,final Class<T> type);

    /**
     * 获取key
     * @param key
     * @param type
     * @param <T>
     * @return
     */
    public <T> Map<String, T> getMap(final String key, final Class<T> type);

    /**
     * 获取key
     * @param key
     * @param type
     * @param <T>
     * @return
     */
    public <T>List<T> getList(final String key,final Class<T> type);
}
