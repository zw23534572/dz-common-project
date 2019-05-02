package com.dazong.common.resp;

import com.alibaba.fastjson.JSON;
import com.dazong.common.CommonStatus;
import com.dazong.common.IResult;

/**
 * 
 * @author luobinwen
 *
 * @param <T>
 */
public class DataResponse<T> extends CommonResponse {

	private static final long serialVersionUID = -4197769202915890604L;

	private T data;

	public DataResponse() {
	}

	public DataResponse(T data) {
		super(CommonStatus.SUCCESS.joinSystemStatusCode());
		this.data = data;
	}

	public DataResponse(Integer retCode, String retMsg) {
		super(retCode, retMsg);
	}
	
	public DataResponse(IResult resultStatus){
		this(resultStatus.getCode(),resultStatus.getMessage());
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
