package com.dazong.common.trans.serialize;

import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.JSON;
import com.dazong.common.trans.SerializeException;


/**
 * json序列化实现
 * 
 * @author hujunzhong
 *
 */
public class JsonSerialize implements IParamSerialize {

	private static final String CHARSET = "utf-8";

	@Override
	public byte[] serialize(Object[] params) {
		if (params == null) {
			return new byte[0];
		}

		try {
			return JSON.toJSONString(params).getBytes(CHARSET);
		} catch (UnsupportedEncodingException e) {
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
		} catch (UnsupportedEncodingException e) {
			throw new SerializeException("json参数反序列化不存在编码:" + CHARSET, e);
		}
	}

}
