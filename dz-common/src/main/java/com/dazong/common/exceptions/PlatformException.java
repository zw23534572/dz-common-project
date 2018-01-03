package com.dazong.common.exceptions;

import com.dazong.common.IResultStatus;

/**
 * 平台异常的父类 。如：公共组件异常
 * 
 * @author wzy
 * 
 */
public class PlatformException extends ApplicationException {

	private static final long serialVersionUID = 1L;

	public PlatformException(String message, Object[] args) {
		super(message, args);
	}

	public PlatformException(int code, String message) {
		super(code, message);
	}

	public PlatformException(int code, String message, Throwable cause) {
		super(code, message, cause);
	}

	public PlatformException(int code, String message, Object[] args) {
		super(code, message, args);
	}

	public PlatformException(String message, Throwable cause, Object[] args) {
		super(message, cause, args);
	}

	/**
	 * 
	 * @param code
	 *            错误码
	 * @param message
	 *            错误信息
	 * @param cause
	 *            原始异常
	 * @param args
	 *            额外参数
	 */
	public PlatformException(int code, String message, Throwable cause, Object[] args) {
		super(code, message, cause, args);
	}

	/**
	 *
	 * @param resultStatus 枚举状态
	 * @param cause 异常信息
	 */
	public PlatformException(IResultStatus resultStatus, Throwable cause) {
		super(resultStatus.getCode(), resultStatus.getMessage(), cause);
	}
}
