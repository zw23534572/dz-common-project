package com.dazong.example.common.constant;

import com.dazong.common.IResultStatus;

public enum ResultEnum implements IResultStatus {
	
	SUCCESS(150200,"成功"),
    FAIL(150500,"失败"),
    USER_IS_NOT_EXIST(150101,"用户不存在！")
	;
	
	private int code;
	private String msg;
	
	private ResultEnum(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	@Override
	public int getCode() {
		return this.code;
	}

	@Override
	public String getMessage() {
		return this.msg;
	}


}
