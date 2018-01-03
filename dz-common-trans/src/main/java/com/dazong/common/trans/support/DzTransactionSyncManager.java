package com.dazong.common.trans.support;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.NamedThreadLocal;

/**
 * 事务上下文管理器
 * @author hujunzhong
 *
 */
public class DzTransactionSyncManager {
	private static final ThreadLocal<Map<String, Object>> resources =
			new NamedThreadLocal<Map<String, Object>>("Transactional resources");

	private static final ThreadLocal<DzTransactionInfo> transactionInfo =
			new NamedThreadLocal<DzTransactionInfo>("Transactional info");
	
	private static final ThreadLocal<DzTransactionObject> retryInfo =
			new NamedThreadLocal<DzTransactionObject>("Transactional Retry info");

	public static final String RETRY_RESOURCE_KEY = "RETRY_RESOURCE_KEY";
	
	public static void setTransactionInfo(DzTransactionInfo transaction){
		transactionInfo.set(transaction);
	}
	
	public static DzTransactionInfo getTransactionInfo(){
		return transactionInfo.get();
	}
	
	public static DzTransactionObject getRetryInfo(){
		return retryInfo.get();
	}
	
	public static void setRetryInfo(DzTransactionObject transaction){
		retryInfo.set(transaction);
	}
	
	public static void clearRetryInfo(){
		retryInfo.remove();
	}
	
	/**
	 * 添加资源
	 * @param key
	 * @param value
	 */
	public static void addResources(String key, Object value){
		Map<String, Object> resMap = resources.get();
		if(resMap == null){
			resMap = new HashMap<>();
			resources.set(resMap);
		}
		
		resMap.put(key, value);
	}
	
	/**
	 * 获取资源
	 * @param key
	 * @return
	 */
	public static Object getResources(String key){
		Map<String, Object> resMap = resources.get();
		if(resMap == null){
			return null;
		}
		
		return resMap.get(key);
	}

	public static void clear() {
		resources.remove();
		transactionInfo.remove();
		retryInfo.remove();
	}
}
