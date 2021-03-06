package com.dazong.common.cache.core.impl;

import com.dazong.common.cache.core.ICacheHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: DanielLi
 * @Date: 2018/1/10
 * @Description:缓存处理的父类
 */
public abstract class AbstractCacheHandler implements ICacheHandler {
    @Override
    public void saveString(String key, String value, final int expireMilliseconds) {

    }

    @Override
    public void saveObject(String key, Object object, final int expireMilliseconds) {

    }

    @Override
    public void saveMap(String key, Map<String, ?> data, final int expireMilliseconds) {
    }

    @Override
    public void saveMapItem(String key, String itemKey, Object value, final int expireMilliseconds) {

    }

    @Override
    public void saveList(String key, List data, final int expireMilliseconds) {

    }

    @Override
    public void saveListItem(String key, Object object, final int expireMilliseconds) {

    }

    @Override
    public void delete(String key) {

    }

    @Override
    public void deleteMapItem(String key, String itemKey) {

    }

    @Override
    public String getString(String key) {
        return null;
    }

    @Override
    public <T> T getObject(String key, Class<T> clazz) {
        return null;
    }

    @Override
    public <T> T getMapItem(String key, String itemKey, Class<T> type) {
        return null;
    }

    @Override
    public <T> Map<String, T> getMap(String key, Class<T> type) {
        return null;
    }

    @Override
    public <T> List<T> getList(String key, Class<T> type) {
        return new ArrayList<>();
    }
    /**
     * Increment an integer value stored of {@code key} by {@code delta}.
     *
     * @param key must not be {@literal null}.
     * @param value
     * @return
     * @see <a href="http://redis.io/commands/incrby">Redis Documentation: INCRBY</a>
     */
    @Override
    public Long incrBy(final String key, final long value, final int expireMilliseconds){
        return 0L;
    }

    @Override
    public Long mapItemIncrBy(final String key, final String itemKey, final long value, final int expireMilliseconds) {
        return 0L;
    }

    @Override
    public <T> T getMapObj(final  String key, final Class T) {
        return null;
    }

}
