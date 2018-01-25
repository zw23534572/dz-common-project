package com.dazong.common.lock;

import com.dazong.common.exceptions.BusinessException;

/**
 * 锁异常，工获取锁失败要抛出此异常
 * @author Sam
 * @version 1.0.0
 */
public class LockException extends BusinessException {

    private static final int ERROR_CODE = 10521;

    public LockException(String message) {
        super(ERROR_CODE,message);
    }

    public LockException(Throwable cause,String message) {
        super(ERROR_CODE,message,cause);
    }
}
