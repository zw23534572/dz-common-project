package com.dazong.common;

/**
 * 序列化接口
 * @author luobinwen
 * @author Sam
 * @author hujunzhong
 *
 */
public interface IObjectSerializer {
	
	/**
     * 将一个对象主序列化
     * @param object 要序列化的对象
     * @return
     */
    <T> byte[] serialize(T object);

    /**
     * 反序列化一个对象
     * @param bytes 字节数组
     * @param type 对象类型
     * @return
     */
    <T> T deserialize(byte[] bytes, Class<T> type);
	
	/**
	 * 把参数序列化成二进制
	 * @param params
	 * @return
	 */
	byte[] serialize(Object[] params);
	
	/**
	 * 把二进制反序列化成参数
	 * @param bytes
	 * @param clz
	 * @return
	 */
	Object[] deserialize(byte[] bytes, Class<?>[] clz);
}
