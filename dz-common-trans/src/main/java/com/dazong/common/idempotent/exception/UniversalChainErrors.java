package com.dazong.common.idempotent.exception;


import com.dazong.common.exceptions.ApplicationException;
import com.dazong.common.exceptions.IErrors;
import com.dazong.common.exceptions.ResponseAndExceptionBuilder;
import com.dazong.common.resp.DataResponse;

/**
 * 万用链 jar 中的异常。
 * 异常区段：1001-1099
 * @author wzy 2017-08-11
 */
public enum UniversalChainErrors implements IErrors<DataResponse<?>> {
	EMPTY_FILTER(1001, "filters is empty"),
	OTHER_THREAD_PROCESSING(1002, "幂等异常，其他线程正在操作"),
	RETURNCLASS_NOT_FOUND(1003, "幂等记录中的returnclass[{0}]不存在"),
	LOCK_ERROR(1004, "lock error"),
	DESERIALIZATION_FAIL(1005, "反序列化失败。idenpotentId={0}"),
	IDENPOTENT_ERROR(1006, "幂等异常"),
	;

	private int code;
	private String message;

	private UniversalChainErrors(int code, String message) {
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
	

}
