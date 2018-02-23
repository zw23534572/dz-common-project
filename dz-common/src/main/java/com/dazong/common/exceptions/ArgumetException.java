package com.dazong.common.exceptions;

import com.dazong.common.IResult;

/**
 * 参数验证异常
 * 
 * @author luobw
 */
public class ArgumetException extends BaseApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ArgumetException(IResult resultStatus) {
		super(resultStatus.getCode(), resultStatus.getMessage());
	}

	public ArgumetException(int code, String message) {
		super(code, message);
	}


}
