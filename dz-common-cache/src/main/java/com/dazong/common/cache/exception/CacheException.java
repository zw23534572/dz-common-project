package com.dazong.common.cache.exception;

import com.dazong.common.cache.manager.CacheFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: DanielLi
 * @Date: 2018/1/10
 * @Description:
 */
public class CacheException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(CacheException.class);
    public CacheException(String message) {
        super(message);
        logger.warn(message);
    }

    public CacheException(Throwable cause) {
        super(cause);
    }

    public CacheException(String message, Throwable cause) {
        super(message, cause);
    }
}
