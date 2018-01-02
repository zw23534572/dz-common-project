package com.dazong.common;

public class ResultStatus implements IResultStatus {

	private int code;

	private String message;

	public ResultStatus(int code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public int getCode() {

		return this.code;
	}

	@Override
	public String getMessage() {

		return this.message;
	}

}
