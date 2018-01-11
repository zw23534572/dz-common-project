package com.dazong.common.cache.serialize;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import org.nustaq.serialization.FSTConfiguration;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author Sam
 * @version 1.0.0
 */
public class FSTObjectSerializer extends AbstractObjectSerializer implements ObjectSerializer {

    private static final FSTConfiguration FST = FSTConfiguration.createDefaultConfiguration();

    @Override
    public <T> byte[] doSerialize(T object) {
        return FST.asByteArray(object);
    }

    @Override
    public <T> T doDeserialize(byte[] bytes,Class<T> type) {
        return (T) FST.asObject(bytes);
    }

    @Override
    public void write(JSONSerializer jsonSerializer, Object o, Object o1, Type type, int i) throws IOException {

    }
}
