package com.dazong.common.cache.serialize;

import org.nustaq.serialization.FSTConfiguration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * @author: DanielLi
 * @date: 2018/1/11
 * @description:
 */
public class FSTRedisSerializer implements RedisSerializer {
    private static final FSTConfiguration FST = FSTConfiguration.createDefaultConfiguration();

    public FSTRedisSerializer() {
    }

    @Override
    public byte[] serialize(Object o) throws SerializationException {
        return FST.asByteArray(o);
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        return FST.asObject(bytes);
    }
}