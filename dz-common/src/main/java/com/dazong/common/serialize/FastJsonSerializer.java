package com.dazong.common.serialize;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.dazong.common.exceptions.SerializeException;

/**
 * fastjson序列化
 * 
 * @author luobinwen
 *
 */
public class FastJsonSerializer extends AbstractObjectSerializer {
	private static final String CHARSET = "utf-8";

	@Override
	public byte[] doSerialize(Object[] params) {
		try {
			return JSON.toJSONString(params).getBytes(CHARSET);
		} catch (Exception e) {
			throw new SerializeException("json序列化异常,当前使用编码为:" + CHARSET, e);
		}

	}

	@Override
	public Object[] doDeserialize(byte[] bytes, Class<?>[] clz) {
		try {
			return JSON.parseArray(new String(bytes, CHARSET), clz).toArray(new Object[] {});
		} catch (Exception e) {
			throw new SerializeException("json反序列化异常,当前使用编码为:" + CHARSET, e);
		}
	}

	@Override
	protected <T> T doDeserialize(byte[] bytes) {
		try {
			return (T) JSON.parse(new String(bytes, CHARSET), Feature.DisableSpecialKeyDetect);
		} catch (Exception e) {
			throw new SerializeException("json序列化异常,当前使用编码为:" + CHARSET, e);
		}
	}

	@Override
	protected <T> byte[] doSerialize(T object) {
		try {
			return JSON.toJSONString(object).getBytes(CHARSET);
		} catch (Exception e) {
			throw new SerializeException("json反序列化异常,当前使用编码为:" + CHARSET, e);
		}
	}

}
