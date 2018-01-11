package com.dazong.common.cache.core.impl;

import com.dazong.common.cache.serialize.FSTObjectSerializer;
import com.dazong.common.cache.serialize.ObjectSerializer;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author: DanielLi
 * @Date: 2018/1/10
 * @Description:
 */

@Service
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
        if (objectSerializer == null){
            objectSerializer = new FSTObjectSerializer();
        }
    }

    /**
     * 缓存框架处理接口
     */

    @Override
    public void saveString(final String key,final String str) {

        if(!StringUtils.isNotBlank(key)){
            return;
        }
        redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.set(key.getBytes(),str.getBytes());
                return true;
            }
        });
    }

    @Override
    public void saveObject(final String key, final Object object) {
        if(!StringUtils.isNotBlank(key) || object == null ){
            return;
        }

        redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.set(key.getBytes(),objectSerializer.serialize(object));
                return true;
            }
        });
    }


    @Override
    public void saveMap(final String key,final  Map<String, ?> data) {
        if(data.isEmpty()){
            return;
        }

        final Map<byte[],byte[]> map = new HashMap<>();
        for (Map.Entry<String,?> entry : data.entrySet()) {
            map.put(entry.getKey().getBytes(),objectSerializer.serialize(entry.getValue()));
        }

        redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.hMSet(key.getBytes(),map);
                return true;
            }
        });
    }

    @Override
    public void saveMapItem(final String key,final String itemKey,final Object value) {
        if (value != null && itemKey != null && key!= null ) {
            redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    redisConnection.hSet(key.getBytes(),itemKey.getBytes(),objectSerializer.serialize(value));
                    return true;
                }
            });
        }
    }

    @Override
    public void saveList(final String key,final List data) {
        if(data.isEmpty()){
            return;
        }

        redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                for (Iterator iter = data.iterator(); iter.hasNext();) {
                    redisConnection.lPush(key.getBytes(),objectSerializer.serialize(iter.next()));
                }

                return true;
            }
        });
    }

    @Override
    public void saveListItem(final String key,final Object object) {
        if (object != null) {

            redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    redisConnection.lPush(key.getBytes(),objectSerializer.serialize(object));
                    return true;
                }
            });
        }
    }

    @Override
    public void delete(final String key) {
        redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.del(key.getBytes());
                return true;
            }
        });
    }

    @Override
    public void deleteMapItem(final String key, final String itemKey) {
        redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.hDel(key.getBytes(),itemKey.getBytes());
                return true;
            }
        });
    }

    @Override
    public String getString(final String key) {
        Validate.notBlank(key);
        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] bytes = connection.get(key.getBytes());
                return new String(bytes);
            }
        });
    }

    @Override
    public <T> T getObject(final String key, final Class<T> clazz) {
        Validate.notBlank(key);
        return redisTemplate.execute(new RedisCallback<T>() {
            @Override
            public T doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] bytes = connection.get(key.getBytes());
                return objectSerializer.deserialize(bytes,clazz);
            }
        });
    }

    @Override
    public <T> T getMapItem(final String key,final String itemKey,final Class<T> type) {

        Validate.notNull(key);
        Validate.notNull(itemKey);
        Validate.notNull(type);

        return redisTemplate.execute(new RedisCallback<T>() {
            @Override
            public T doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] value = connection.hGet(key.getBytes(),itemKey.getBytes());
                return objectSerializer.deserialize(value,type);
            }
        });
    }

    @Override
    public <T> Map<String, T> getMap(final String key, final Class<T> type) {
        Validate.notNull(key);
        return redisTemplate.execute(new RedisCallback<Map<String, T>>() {
            @Override
            public Map<String, T> doInRedis(RedisConnection connection) throws DataAccessException {
                Map<byte[],byte[]> value = connection.hGetAll(key.getBytes());
                if (value != null && !value.isEmpty()) {
                    Map<String,T> map = new HashMap<>();
                    for (Map.Entry<byte[],byte[]> entry : value.entrySet()) {
                        String itemKey = new String(entry.getKey());
                        T itemValue = objectSerializer.deserialize(entry.getValue(),type);
                        map.put(itemKey,itemValue);
                    }
                    return map;
                }
                return null;
            }
        });
    }

    @Override
    public <T>List<T> getList(final String key,final Class<T> type) {
        Validate.notNull(key);
        Validate.notNull(type);
        return redisTemplate.execute(new RedisCallback<List<T>>() {
            @Override
            public List<T> doInRedis(RedisConnection connection) throws DataAccessException {
                Long len = connection.lLen(key.getBytes());
                if (len > 0) {
                    List<byte[]> list  = connection.lRange(key.getBytes(), 0L, len);
                    List<T> targetlist = new ArrayList<>(len.intValue());
                    for (byte[] l : list) {
                        targetlist.add(objectSerializer.deserialize(l,type));
                    }
                    return targetlist;
                }
                return null;
            }
        });
    }
}
