package com.dazong.common.trans.support;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.NamedThreadLocal;

/**
 * 事务上下文管理器
 * 
 * @author hujunzhong
 *
 */
public class DzTransactionSyncManager {
	private DzTransactionSyncManager() {
		throw new IllegalStateException("Utility class");
	}

	private static final ThreadLocal<Map<String, Object>> RESOURCES = new NamedThreadLocal<>("Transactional resources");

	private static final ThreadLocal<DzTransactionInfo> TRANSACTION_INFO = new NamedThreadLocal<>("Transactional info");

	private static final ThreadLocal<DzTransactionObject> RETRY_INFO = new NamedThreadLocal<>(
			"Transactional Retry info");

	public static final String RETRY_RESOURCE_KEY = "RETRY_RESOURCE_KEY";

	public static void setTransactionInfo(DzTransactionInfo transaction) {
		TRANSACTION_INFO.set(transaction);
	}

	public static DzTransactionInfo getTransactionInfo() {
		return TRANSACTION_INFO.get();
	}

	public static DzTransactionObject getRetryInfo() {
		return RETRY_INFO.get();
	}

	public static void setRetryInfo(DzTransactionObject transaction) {
		RETRY_INFO.set(transaction);
	}

	public static void clearRetryInfo() {
		RETRY_INFO.remove();
	}

	/**
	 * 添加资源
	 * 
	 * @param key
	 * @param value
	 */
	public static void addResources(String key, Object value) {
		Map<String, Object> resMap = RESOURCES.get();
		if (resMap == null) {
			resMap = new HashMap<>();
			RESOURCES.set(resMap);
		}

		resMap.put(key, value);
	}

	/**
	 * 获取资源
	 * 
	 * @param key
	 * @return
	 */
	public static Object getResources(String key) {
		Map<String, Object> resMap = RESOURCES.get();
		if (resMap == null) {
			return null;
		}

		return resMap.get(key);
	}

	public static void clear() {
		RESOURCES.remove();
		TRANSACTION_INFO.remove();
		RETRY_INFO.remove();
	}
}
