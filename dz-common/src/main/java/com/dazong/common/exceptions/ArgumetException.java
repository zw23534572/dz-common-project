package com.dazong.common.exceptions;

/**
 * 参数验证异常
 * @author luobw
 */
public class ArgumetException extends BusinessException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    public ArgumetException(int code, String message) {
        super(code, message);
    }
}
