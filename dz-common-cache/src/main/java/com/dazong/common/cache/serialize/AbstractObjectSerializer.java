package com.dazong.common.cache.serialize;


import java.math.BigDecimal;

/**
 * 基类对象串行器
 * @author Sam
 * @version 1.0.0
 */
public abstract class AbstractObjectSerializer implements ObjectSerializer {
    @Override
    public <T> byte[] serialize(T object) {
        if (object == null){
            return new byte[0];
        }
        if (object instanceof byte[]) {
            return (byte[]) object;
        }
        if (object instanceof String) {
            return object.toString().getBytes();
        }
        if (object instanceof Long ||
                object instanceof Integer ||
                object instanceof Double ||
                object instanceof Short ||
                object instanceof BigDecimal){
            return object.toString().getBytes();
        }

        return doSerialize(object);
    }

    @Override
    public <T> T deserialize(byte[] bytes,Class<T> type) {
        if (bytes == null || bytes.length < 1) {
            return null;
        }
        if (type == String.class) {
            return (T) new String(bytes);
        }
        if (type == Integer.class) {
            return (T) Integer.valueOf(new String(bytes));
        }
        if (type == Long.class) {
            return (T) Long.valueOf(new String(bytes));
        }
        if (type == Double.class) {
            return (T) Double.valueOf(new String(bytes));
        }
        if (type == Short.class) {
            return (T) Short.valueOf(new String(bytes));
        }
        if (type == BigDecimal.class) {
            return (T) new BigDecimal(new String(bytes));
        }

        return doDeserialize(bytes,type);
    }

    /**
     * 反序列化
     * @param bytes
     * @param type
     * @param <T>
     * @return
     */
    public abstract <T> T doDeserialize(byte[] bytes,Class<T> type);

    /**
     * 序列化
     * @param object
     * @param <T>
     * @return
     */
    protected abstract <T> byte[]  doSerialize(T object);
}
