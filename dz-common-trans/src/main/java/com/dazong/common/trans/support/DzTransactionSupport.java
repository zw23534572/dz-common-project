package com.dazong.common.trans.support;

/**
 * 公共工具类
 * 
 * @author hujunzhong
 *
 */
public class DzTransactionSupport {
	private DzTransactionSupport() {
		throw new IllegalStateException("Utility class");
	}

	public static String getDefaultIfNull(String src, String def) {
		if (src == null || src.isEmpty()) {
			return def;
		}

		return src;
	}

	/**
	 * 是否使用某个类
	 * 
	 * @param clazz
	 * @return
	 */
	public static boolean usedClass(String clazz) {
		try {
			Class.forName(clazz);
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	/**
	 * 当前是否正在重试事务
	 * 
	 * @return
	 */
	public static boolean isRetrying() {
		DzTransactionInfo txInfo = DzTransactionSyncManager.getTransactionInfo();
		if (txInfo == null) {
			return false;
		}

		return txInfo.getTransactionStatus() != null && txInfo.getTransactionStatus().isRetry();
	}
}
