package com.dazong.common.cache.serialize;

import org.nustaq.serialization.FSTConfiguration;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author: DanielLi
 * @date: 2018/1/11
 * @description:
 */
public class FstRedisSerializer implements RedisSerializer {
    private static final FSTConfiguration FST = FSTConfiguration.createDefaultConfiguration();

    @Override
    public byte[] serialize(Object o)  {
        return FST.asByteArray(o);
    }

    @Override
    public Object deserialize(byte[] bytes)  {
        return FST.asObject(bytes);
    }
}