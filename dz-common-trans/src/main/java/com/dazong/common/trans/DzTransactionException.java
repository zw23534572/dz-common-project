package com.dazong.common.trans;

import com.dazong.common.CommonStatus;
import com.dazong.common.exceptions.BaseApplicationException;


/**
 * 异常类
 * 
 * @author hujunzhong
 *
 */
public class DzTransactionException extends BaseApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3906827017056676612L;
	
	public DzTransactionException(String message) {
		super(CommonStatus.RETRY_UTIL_ERROR.getCode(), message);
	}
	
	public DzTransactionException(String message, Throwable cause) {
		super(CommonStatus.RETRY_UTIL_ERROR.getCode(), message, cause);
	}

	

}
