package com.dazong.common.exceptions;

import com.dazong.common.IResultStatus;

/**
 * 参数验证异常
 * 
 * @author luobw
 */
public class ArgumetException extends BusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ArgumetException(IResultStatus resultStatus) {
		super(resultStatus.getCode(), resultStatus.getMessage());
	}

	public ArgumetException(int code, String message) {
		super(code, message);
	}
}
