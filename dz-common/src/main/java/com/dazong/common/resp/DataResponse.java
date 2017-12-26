package com.dazong.common.resp;

import com.alibaba.fastjson.JSON;

public class DataResponse<T> extends CommonResponse {

	private static final long serialVersionUID = -4197769202915890604L;

	private T data;

	public DataResponse() {
	}

	public DataResponse(T data) {
		super(CommonRespStatus.SUCCESS.getCode(), CommonRespStatus.SUCCESS.getCodeDesc());
		this.data = data;
	}

	public DataResponse(Integer retCode, String retMsg) {
		super(retCode, retMsg);
	}

	public DataResponse(Integer retCode, String retMsg, T data) {
		super(retCode, retMsg);
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
