package com.dazong.common;

import com.dazong.common.exceptions.ApplicationException;
import com.dazong.common.exceptions.ResponseAndExceptionBuilder;
import com.dazong.common.resp.DataResponse;

/**
 * <pre>
 * 公共的业务异常(BusinessException)枚举类。各个模块定义自己的异常枚举类。
 * 用来统一平台的公共返回码，如：成功的返回码，参数异常的返回码....
 * </pre>
 * 
 * <b>异常码区段： 100-200</b>
 * 
 * @author wzy 2017-08-11
 */
public enum CommonStatus implements IErrors<DataResponse<?>> {

	/**
	 * 参数异常
	 */
	ILLEGAL_PARAM(101, "参数[{0}]错误"),
	/**
	 * 成功
	 */
	SUCCESS(200, "成功"),
	/**
	 * 处理失败
	 */
	FAIL(400, "处理失败！{0}"),
	/**
	 * 系统错误
	 */
	ERROR(500, "系统错误！{0}"),

	/**
	 * mq系统错误
	 */
	MQ_ERROR(505, "消息组件发生异常");

	private int code;
	private String message;

	private CommonStatus(int code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public DataResponse<?> parse() {
		return ResponseAndExceptionBuilder.parseMsg(this, this.message);
	}

	@Override
	public DataResponse<?> parse(Object... args) {
		return ResponseAndExceptionBuilder.parseMsg(this, this.message, args);
	}

	@Override
	public DataResponse<?> parseMsg(String message, Object... args) {
		return ResponseAndExceptionBuilder.parseMsg(this, this.message, args);
	}

	@Override
	public ApplicationException exp() {
		return ResponseAndExceptionBuilder.expMsg(this, this.message, null);
	}

	@Override
	public ApplicationException exp(Object... args) {
		return ResponseAndExceptionBuilder.expMsg(this, this.message, null, args);
	}

	@Override
	public ApplicationException exp(Throwable cause, Object... args) {
		return ResponseAndExceptionBuilder.expMsg(this, this.message, cause, args);
	}

	@Override
	public ApplicationException expMsg(String message, Object... args) {
		return ResponseAndExceptionBuilder.expMsg(this, message, null, args);
	}

	@Override
	public ApplicationException expMsg(String message, Throwable cause, Object... args) {
		return ResponseAndExceptionBuilder.expMsg(this, message, cause, args);
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public IResultStatus joinSystemStatusCode() {
		Integer fullCode = Integer.parseInt(Integer.toString(ApplicationInfo.instance().getSystemCode()) + this.code);
		return new ResultStatus(fullCode, this.message);

	}

	public IResultStatus joinSystemStatusCode(String message) {
		Integer fullCode = Integer.parseInt(Integer.toString(ApplicationInfo.instance().getSystemCode()) + this.code);
		return new ResultStatus(fullCode, message);

	}

}
