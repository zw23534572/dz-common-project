package com.dazong.common.util.serialize;

/**
 * 对象串行化接口，定义对象的串行化与反串行化接口
 * @author Sam
 * @version 1.0.0
 */
public interface ObjectSerializer {
    /**
     * 将一个对象主序列化
     * @param object 要序列化的对象
     * @return
     */
    <T> byte[] serialize(T object);

    /**
     * 反序列化一个对象
     * @param bytes 字节数组
     * @return
     */
    <T> T deserialize(byte[] bytes, Class<T> type);
}
