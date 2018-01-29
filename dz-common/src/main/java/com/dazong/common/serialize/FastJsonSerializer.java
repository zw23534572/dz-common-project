package com.dazong.common.serialize;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.dazong.common.exceptions.SerializeException;

public class FastJsonSerializer extends AbstractObjectSerializer {
	private static final String CHARSET = "utf-8";

	@Override
	public byte[] serialize(Object[] params) {
		if (params == null) {
			return new byte[0];
		}

		try {
			return JSON.toJSONString(params).getBytes(CHARSET);
		} catch (Exception e) {
			throw new SerializeException("json参数序列化不存在编码:" + CHARSET, e);
		}
	}

	@Override
	public Object[] deserialize(byte[] bytes, Class<?>[] clz) {
		if (bytes == null || clz == null) {
			return new Object[0];
		}

		try {
			return JSON.parseArray(new String(bytes, CHARSET), clz).toArray(new Object[] {});
		} catch (Exception e) {
			throw new SerializeException("json参数反序列化不存在编码:" + CHARSET, e);
		}
	}

	@Override
	protected <T> T doDeserialize(byte[] bytes) {
		try {
			return (T) JSON.parse(new String(bytes, CHARSET), Feature.DisableSpecialKeyDetect);
		} catch (Exception e) {
			throw new SerializeException("json参数序列化不存在编码:" + CHARSET, e);
		}
	}

	@Override
	protected <T> byte[] doSerialize(T object) {
		try {
			return JSON.toJSONString(object).getBytes(CHARSET);
		} catch (Exception e) {
			throw new SerializeException("json参数序列化不存在编码:" + CHARSET, e);
		}
	}

}
