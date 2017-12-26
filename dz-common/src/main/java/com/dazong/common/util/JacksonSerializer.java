package com.dazong.common.util;

import org.apache.commons.lang3.SerializationException;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;

public class JacksonSerializer {

	private static final ObjectMapper mapper;
	
	static {
		mapper = new ObjectMapper();
		mapper.enableDefaultTyping(DefaultTyping.NON_FINAL, As.PROPERTY);
	}

	public static String serialize(Object source){
		if (source == null) {
			return null;
		}
		try {
			return mapper.writeValueAsString(source);
		} catch (JsonProcessingException e) {
			throw new SerializationException("Could not write JSON: " + e.getMessage(), e);
		}
	}
	
	public static Object deserialize(String source) throws SerializationException {

		if (source == null) {
			return null;
		}

		try {
			return mapper.readValue(source, Object.class);
		} catch (Exception ex) {
			throw new SerializationException("Could not read JSON: " + ex.getMessage(), ex);
		}
	}
}