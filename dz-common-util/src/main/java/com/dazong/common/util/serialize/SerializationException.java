package com.dazong.common.util.serialize;

import com.dazong.common.exceptions.BusinessException;

/**
 * @author Sam
 * @version 1.0.0
 */
public class SerializationException extends BusinessException {

    public SerializationException( Throwable cause) {
        super(100100, "对象序列化异常", cause);
    }
}
