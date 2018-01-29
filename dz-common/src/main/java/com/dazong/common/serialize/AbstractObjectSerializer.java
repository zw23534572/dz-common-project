package com.dazong.common.serialize;

import java.math.BigDecimal;

import com.dazong.common.IObjectSerializer;

/**
 * 
 * @author luobinwen
 *
 */
public abstract class AbstractObjectSerializer implements IObjectSerializer {

	@Override
	public <T> byte[] serialize(T object) {
		if (object == null) {
			return new byte[0];
		}
		if (object instanceof byte[]) {
			return (byte[]) object;
		}
		if (object instanceof String) {
			return object.toString().getBytes();
		}
		if (object instanceof Long || object instanceof Integer || object instanceof Double || object instanceof Short
				|| object instanceof BigDecimal) {
			return object.toString().getBytes();
		}

		return doSerialize(object);
	}

	@Override
	public <T> T deserialize(byte[] bytes, Class<T> type) {
		if (bytes == null || bytes.length < 1) {
			return null;
		}
		if (type == String.class) {
			return (T) new String(bytes);
		}
		if (type == Integer.class) {
			return ((T) Integer.valueOf(new String(bytes)));
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

		return doDeserialize(bytes);
	}

	/**
	 * 把参数序列化成二进制
	 * @param params
	 * @return
	 */
	@Override
	public abstract byte[] serialize(Object[] params);
	
	/**
	 * 把二进制反序列化成参数
	 * @param bytes
	 * @param clz
	 * @return
	 */
	@Override
	public abstract Object[] deserialize(byte[] bytes, Class<?>[] clz);

	/**
	 * 反序列化
	 * 
	 * @param bytes
	 * @param <T>
	 * @return
	 */
	protected abstract <T> T doDeserialize(byte[] bytes);

	/**
	 * 序列化
	 * 
	 * @param object
	 * @param <T>
	 * @return
	 */
	protected abstract <T> byte[] doSerialize(T object);

}
