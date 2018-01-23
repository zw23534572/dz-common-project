package com.dazong.common.cache.core.impl;

import com.dazong.common.cache.serialize.JdkSerializer;
import com.dazong.common.cache.serialize.ObjectSerializer;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author: DanielLi
 * @Date: 2018/1/10
 * @Description:Redis文档说明:http://blog.csdn.net/zhu_xun/article/details/16806285
 */

@Service
public class RedisCacheHandler extends AbstractCacheHandler implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(RedisCacheHandler.class);
    public static final String IS_NULL_VALUE_WARN = "获取到的value值为null";

    private static final String CHARACTER = "UTF-8";

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
            objectSerializer = new JdkSerializer();
        }
    }

    /**
     * 获取字符串
     */

    @Override
    public void saveString(final String key,final String str, final int expireMilliseconds) {
        Validate.notBlank(key);

        redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection){
                try {
                    redisConnection.pSetEx(key.getBytes(CHARACTER),expireMilliseconds, str.getBytes(CHARACTER));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
    }

    /**
     * 获取对象,需要序列化
     * @param key
     * @param object
     * @param expireMilliseconds
     */
    @Override
    public void saveObject(final String key, final Object object, final int expireMilliseconds) {
        Validate.notBlank(key);
        Validate.notNull(object);

        redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection){
                try {
                    redisConnection.pSetEx(key.getBytes(CHARACTER), expireMilliseconds, objectSerializer.serialize(object));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
    }


    /**
     * 获取Map,需要序列化每一个元素
     * @param key
     * @param data
     * @param expireMilliseconds
     */
    @Override
    public void saveMap(final String key,final  Map<String, ?> data, final int expireMilliseconds) {
        Validate.notBlank(key);
        Validate.notNull(data);

        final Map<byte[],byte[]> map = new HashMap<>(10);
        try {
            for (Map.Entry<String,?> entry : data.entrySet()) {
                map.put(entry.getKey().getBytes(CHARACTER),objectSerializer.serialize(entry.getValue()));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection){
                try {
                    redisConnection.hMSet(key.getBytes(CHARACTER), map);
                    redisConnection.pExpire(key.getBytes(CHARACTER), expireMilliseconds);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
    }

    /**
     * 向名称为key的hash中添加元素field<—>value
     * @param key
     * @param itemKey
     * @param value
     * @param expireMilliseconds
     */
    @Override
    public void saveMapItem(final String key,final String itemKey,final Object value, final int expireMilliseconds) {
        Validate.notBlank(key);
        Validate.notNull(itemKey);
        Validate.notNull(value);

        redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection){
                try {
                    redisConnection.hSet(key.getBytes(CHARACTER), itemKey.getBytes(CHARACTER),objectSerializer.serialize(value));
                    redisConnection.pExpire(key.getBytes(CHARACTER), expireMilliseconds);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
    }

    /***
     * 保存List,需要序列化
     * @param key
     * @param data
     * @param expireMilliseconds
     */
    @Override
    public void saveList(final String key,final List data, final int expireMilliseconds) {
        Validate.notBlank(key);
        Validate.notNull(data);

        redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection){
                for (Iterator iter = data.iterator(); iter.hasNext();) {
                    try {
                        redisConnection.lPush(key.getBytes(CHARACTER), objectSerializer.serialize(iter.next()));
                        redisConnection.pExpire(key.getBytes(CHARACTER), expireMilliseconds);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }
        });
    }

    /**
     * 在名称为key的list头添加一个值为value的 元素
     * @param key
     * @param object
     * @param expireMilliseconds
     */
    @Override
    public void saveListItem(final String key,final Object object, final int expireMilliseconds) {
        Validate.notBlank(key);
        if (object == null) {
            return;
        }
            redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection redisConnection){
                    try {
                        redisConnection.lPush(key.getBytes(CHARACTER), objectSerializer.serialize(object));
                        redisConnection.pExpire(key.getBytes(CHARACTER), expireMilliseconds);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
            });
    }

    /**
     * 删除元素
     * @param key
     */
    @Override
    public void delete(final String key) {
        Validate.notBlank(key);
        redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection){
                try {
                    redisConnection.del(key.getBytes(CHARACTER));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
    }

    @Override
    public void deleteMapItem(final String key, final String itemKey) {
        Validate.notBlank(key);
        redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection){
                try {
                    redisConnection.hDel(key.getBytes(CHARACTER),itemKey.getBytes(CHARACTER));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
    }

    /**
     * 获取字符串
     * @param key
     * @return
     */
    @Override
    public String getString(final String key) {
        Validate.notBlank(key);
        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection){
                byte[] bytes = new byte[0];
                try {
                    bytes = connection.get(key.getBytes(CHARACTER));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if(null == bytes){
                    logger.info(IS_NULL_VALUE_WARN);
                    return "";
                }
                return new String(bytes);
            }
        });
    }

    /**
     * 获取对象,需要反序列化
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    @Override
    public <T> T getObject(final String key, final Class<T> clazz) {
        Validate.notBlank(key);
        return redisTemplate.execute(new RedisCallback<T>() {
            @Override
            public T doInRedis(RedisConnection connection){
                byte[] bytes = new byte[0];
                try {
                    bytes = connection.get(key.getBytes(CHARACTER));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if(null == bytes){
                    logger.info(IS_NULL_VALUE_WARN);
                    return null;
                }
                return objectSerializer.deserialize(bytes,clazz);
            }
        });
    }

    @Override
    public <T> T getMapItem(final String key,final String itemKey,final Class<T> type) {
        Validate.notBlank(key);
        Validate.notNull(itemKey);
        Validate.notNull(type);
        return redisTemplate.execute(new RedisCallback<T>() {
            @Override
            public T doInRedis(RedisConnection connection){
                byte[] value = new byte[0];
                try {
                    value = connection.hGet(key.getBytes(CHARACTER),itemKey.getBytes(CHARACTER));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if(null == value){
                    logger.info(IS_NULL_VALUE_WARN);
                    return null;
                }
                return objectSerializer.deserialize(value,type);
            }
        });
    }

    /**
     * 获取Map集合,需要反序列化
     * @param key
     * @param type
     * @param <T>
     * @return
     */
    @Override
    public <T> Map<String, T> getMap(final String key, final Class<T> type) {
        Validate.notBlank(key);
        return redisTemplate.execute(new RedisCallback<Map<String, T>>() {
            @Override
            public Map<String, T> doInRedis(RedisConnection connection) {
                Map<byte[],byte[]> value = null;
                try {
                    value = connection.hGetAll(key.getBytes(CHARACTER));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if(value == null || value.isEmpty()){
                    logger.info(IS_NULL_VALUE_WARN);
                    return null;
                }
                Map<String,T> map = new HashMap<>(10);
                for (Map.Entry<byte[],byte[]> entry : value.entrySet()) {
                    String itemKey = new String(entry.getKey());
                    T itemValue = objectSerializer.deserialize(entry.getValue(),type);
                    map.put(itemKey,itemValue);
                }
                return map;
            }
        });
    }

    /**
     * 获取列表,需要反序列化
     * @param key
     * @param type
     * @param <T>
     * @return
     */
    @Override
    public <T>List<T> getList(final String key,final Class<T> type) {
        Validate.notBlank(key);
        Validate.notNull(type);
        return redisTemplate.execute(new RedisCallback<List<T>>() {
            @Override
            public List<T> doInRedis(RedisConnection connection){
                List<T> targetlist = null;
                try {
                    Long len = connection.lLen(key.getBytes(CHARACTER));
                    if(len <= 0){
                        logger.info(IS_NULL_VALUE_WARN);
                        return new ArrayList<>();
                    }
                    List<byte[]> list  = connection.lRange(key.getBytes(CHARACTER), 0L, len);
                    targetlist = new ArrayList<>(len.intValue());
                    for (byte[] l : list) {
                        targetlist.add(objectSerializer.deserialize(l,type));
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return targetlist;
            }
        });
    }
}
