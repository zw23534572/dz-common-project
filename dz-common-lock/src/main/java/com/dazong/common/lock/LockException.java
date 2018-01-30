package com.dazong.common.lock;

import com.dazong.common.CommonStatus;
import com.dazong.common.exceptions.BusinessException;

/**
 * 锁异常，工获取锁失败要抛出此异常
 * @author Sam
 * @version 1.0.0
 */
public class LockException extends BusinessException {

    public LockException(String message) {
        super(CommonStatus.LOCKING_ERROR.getCode(),message);
    }

    public LockException(Throwable cause,String message) {
        super(CommonStatus.LOCKING_ERROR.getCode(),message,cause);
    }
}
