package com.dazong.common.cache.exception;

/**
 * @author: DanielLi
 * @Date: 2018/1/10
 * @Description:
 */
public class CacheException extends RuntimeException {

    public CacheException(String message) {
        super(message);
    }

    public CacheException(Throwable cause) {
        super(cause);
    }

    public CacheException(String message, Throwable cause) {
        super(message, cause);
    }
}
