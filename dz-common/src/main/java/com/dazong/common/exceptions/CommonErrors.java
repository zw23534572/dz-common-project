package com.dazong.common.exceptions;

import com.alibaba.fastjson.JSON;
import com.dazong.common.resp.CommonRespStatus;
import com.dazong.common.resp.DataResponse;

/**
 * <pre>
 * 公共的业务异常(BusinessException)枚举类。各个模块定义自己的异常枚举类。
 * 用来统一平台的公共返回码，如：成功的返回码，参数异常的返回码....
 * </pre>
 * <b>异常码区段： 100-200</b>
 * @author wzy 2017-08-11
 */
public enum CommonErrors implements IErrors<DataResponse<?>> {
//	SUCCESS(100, "成功"),
	SUCCESS(CommonRespStatus.SUCCESS.code, "成功"),
	ILLEGAL_PARAM(101, "参数[{0}]错误"),
	SYSTEM_ERROR(199, "系统异常"),
	DB_ERROR(501, "数据库异常：{0}");

	private int code;
	private String message;

	private CommonErrors(int code, String message) {
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
	
	public static void main(String[] args) {
//		throw CommonErrors.SUCCESS.exp();
//		throw CommonErrors.ILLEGAL_PARAM.exp("arg0");
		System.out.println(JSON.toJSONString(CommonErrors.SUCCESS.parse()));
		System.out.println(JSON.toJSONString(CommonErrors.ILLEGAL_PARAM.parse("arg0")));
	}

}
