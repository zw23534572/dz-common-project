package com.dazong.common.cache.serialize;

import org.nustaq.serialization.FSTConfiguration;

/**
 * @author Sam
 * @version 1.0.0
 */
public class FstObjectSerializer extends AbstractObjectSerializer  {

    private static final FSTConfiguration FST = FSTConfiguration.createDefaultConfiguration();

    @Override
    public <T> byte[] doSerialize(T object) {
        return FST.asByteArray(object);
    }

    @Override
    public <T> T doDeserialize(byte[] bytes,Class<T> type) {
        return (T) FST.asObject(bytes);
    }

}
