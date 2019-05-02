package com.dazong.common.idempotent.exception;



import com.dazong.common.IErrors;
import com.dazong.common.exceptions.BaseApplicationException;
import com.dazong.common.exceptions.ResponseAndExceptionBuilder;
import com.dazong.common.resp.CommonResponse;

/**
 * 万用链 jar 中的异常。
 * 异常区段：1001-1099
 * @author wzy 2017-08-11
 */
public enum IdempotentErrors implements IErrors<CommonResponse> {
	EMPTY_FILTER(1001, "filters is empty"),
	OTHER_THREAD_PROCESSING(1002, "幂等异常，其他线程正在操作"),
	RETURNCLASS_NOT_FOUND(1003, "幂等记录中的returnclass[{0}]不存在"),
	LOCK_ERROR(1004, "lock error"),
	DESERIALIZATION_FAIL(1005, "反序列化失败。idenpotentId={0}"),
	IDENPOTENT_ERROR(1006, "幂等异常"),
	;

	private int code;
	private String message;

	private IdempotentErrors(int code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public CommonResponse parse() {
		return ResponseAndExceptionBuilder.parseMsg(this, this.message);
	}

	@Override
	public CommonResponse parse(Object... args) {
		return ResponseAndExceptionBuilder.parseMsg(this, this.message, args);
	}

	@Override
	public CommonResponse parseMsg(String message, Object... args) {
		return ResponseAndExceptionBuilder.parseMsg(this, this.message, args);
	}

	@Override
	public BaseApplicationException exp() {
		return ResponseAndExceptionBuilder.expMsg(this, this.message, null);
	}

	@Override
	public BaseApplicationException exp(Object... args) {
		return ResponseAndExceptionBuilder.expMsg(this, this.message, null, args);
	}

	@Override
	public BaseApplicationException exp(Throwable cause, Object... args) {
		return ResponseAndExceptionBuilder.expMsg(this, this.message, cause, args);
	}

	@Override
	public BaseApplicationException expMsg(String message, Object... args) {
		return ResponseAndExceptionBuilder.expMsg(this, message, null, args);
	}

	@Override
	public BaseApplicationException expMsg(String message, Throwable cause, Object... args) {
		return ResponseAndExceptionBuilder.expMsg(this, message, cause, args);
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	

}
