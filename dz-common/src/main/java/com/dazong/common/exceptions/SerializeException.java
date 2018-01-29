package com.dazong.common.exceptions;

import com.dazong.common.CommonStatus;

/**
 * 序列化异常
 * @author luobinwen
 *
 */
public class SerializeException extends BaseApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2708917060582630145L;

	public SerializeException(String message, Throwable cause) {
		super(CommonStatus.SERIALIZE_ERROR.getCode(), message, cause);
	}

}
