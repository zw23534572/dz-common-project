package com.dazong.common.exceptions;

/**
 * 参数验证异常
 * @author wzy on 2017/8/16.
 */
public class ArgsException extends BusinessException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ArgsException(String message) {
        super(message);
    }

    public ArgsException(int code, String message) {
        super(code, message);
    }
}
