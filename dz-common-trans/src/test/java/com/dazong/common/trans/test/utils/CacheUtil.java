package com.dazong.common.trans.test.utils;

import java.util.concurrent.ConcurrentHashMap;

public class CacheUtil {
	
	private static ConcurrentHashMap<String,Object> cacheMap = new ConcurrentHashMap<String, Object>();
	
	public static void put(String key,Object obj){
		cacheMap.put(key, obj);
	}

	public static Object get(String key){
		return cacheMap.get(key);
	}
}
