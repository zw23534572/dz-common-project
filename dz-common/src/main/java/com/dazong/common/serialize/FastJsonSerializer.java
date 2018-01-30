package com.dazong.common.serialize;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
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
	protected <T> T doDeserialize(byte[] bytes, Class<T> type) {
		try {
//			ParserConfig config=new ParserConfig();
//			config.addAccept("com.dazong.");
//			JSON.parseObject(new String(bytes, CHARSET), type, config, null);
			
			return (T) JSON.parseObject(new String(bytes, CHARSET),type);
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
