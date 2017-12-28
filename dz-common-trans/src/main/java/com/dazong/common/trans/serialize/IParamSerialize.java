package com.dazong.common.trans.serialize;

/**
 * 参数序列化接口
 * @author hujunzhong
 *
 */
public interface IParamSerialize {
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
