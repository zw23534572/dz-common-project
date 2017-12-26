package com.dazong.common.exceptions;

/**
 * 公用业务异常,大宗所有异常的公共基类.
 * 
 * @author luobw
 *
 */
public class BusinessException extends ApplicationException {

    private static final long serialVersionUID = -8943298004576967279L;
    
    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public BusinessException(String message, Object[] args) {
        super(message, args);
    }
    
    public BusinessException(String message, Throwable cause, Object[] args) {
        super(message, cause, args);
    }
    
    public BusinessException(int code, String message) {
        super(code, message);
    }

    public BusinessException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }

    

    public BusinessException(int code, String message, Object[] args) {
        super(code, message, args);
    }

    /**
     * 
     * @param code
     *            错误码
     * @param message
     *            错误信息
     * @param cause
     *            原始异常
     * @param args
     *            额外参数
     */
    public BusinessException(int code, String message, Throwable cause, Object[] args) {
        super(code, message, cause, args);
    }

}
