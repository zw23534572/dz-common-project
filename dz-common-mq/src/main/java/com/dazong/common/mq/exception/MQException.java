package com.dazong.common.mq.exception;

/**
 * 消息组件异常
 * @author huqichao
 * @date 2017-10-30 15:31
 */
public class MQException extends RuntimeException {

    public MQException(String message, Object... objs){
        super(String.format(message, objs));
    }

    public MQException(Throwable cause, String message, Object... objs) {
        super(String.format(message, objs), cause);
    }

    public MQException(Throwable cause) {
        super(cause);
    }
}
