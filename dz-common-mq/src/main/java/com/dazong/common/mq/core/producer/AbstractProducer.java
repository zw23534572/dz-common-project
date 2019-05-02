package com.dazong.common.mq.core.producer;

import com.dazong.common.mq.domian.DZMessage;
import com.dazong.common.mq.exception.MQException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author huqichao
 * @date 2017-10-30 15:31
 **/
public abstract class AbstractProducer {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 发送消息
     * @param message 消息
     * @throws MQException
     */
    public abstract void sendMessage(DZMessage message) throws MQException;
}
