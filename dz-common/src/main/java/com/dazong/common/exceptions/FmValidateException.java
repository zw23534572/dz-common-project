package com.dazong.common.exceptions;

/**
 * 参数验证异常
 * @author wzy on 2017/8/16.
 */
public class FmValidateException extends BusinessException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FmValidateException(String message) {
        super(message);
    }

    public FmValidateException(int code, String message) {
        super(code, message);
    }
}
