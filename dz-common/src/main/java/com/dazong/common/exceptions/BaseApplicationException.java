package com.dazong.common.exceptions;

import java.util.HashMap;
import java.util.Map;

import com.dazong.common.IResultStatus;

/**
 * 异常基类，扩展了异常码code 与 异常参数 args
 * 
 * @author wzy
 * 
 */
public abstract class BaseApplicationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * 异常错误码
	 */
	protected final Integer code;

	/**
	 * 异常信息的参数
	 */
	protected final transient Object[] args;

	public BaseApplicationException(IResultStatus status) {
		this(status.getCode(), status.getMessage());
	}

	public BaseApplicationException(String message, Object[] args) {
		this(null, message, null, args);
	}

	public BaseApplicationException(String message, Throwable cause, Object[] args) {
		this(null, message, cause, args);
	}

	public BaseApplicationException(Integer code, String message) {
		this(code, message, null, null);
	}

	public BaseApplicationException(Integer code, String message, Throwable cause) {
		this(code, message, cause, null);
	}

	public BaseApplicationException(Integer code, String message, Object[] args) {
		this(code, message, null, args);
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
	public BaseApplicationException(Integer code, String message, Throwable cause, Object[] args) {
		super(message, cause);
		this.code = code;
		this.args = args;
	}

	public Integer getCode() {
		return code;
	}

	public Object[] getArgs() {
		return args;
	}

	/**
	 * @return
	 */
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<>(2);
		map.put("code", this.code);
		map.put("msg", this.getMessage());
		return map;
	}

}
