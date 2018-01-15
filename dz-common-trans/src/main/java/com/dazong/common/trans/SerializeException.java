package com.dazong.common.trans;

import com.dazong.common.CommonStatus;
import com.dazong.common.exceptions.BaseApplicationException;

/**
 * 序列化异常
 * @author luobinwen
 *
 */
public class SerializeException extends BaseApplicationException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6111724469017435033L;

	public SerializeException(String message, Throwable cause) {
		super(CommonStatus.RETRY_UTIL_ERROR.getCode(), message, cause);
	}
}
