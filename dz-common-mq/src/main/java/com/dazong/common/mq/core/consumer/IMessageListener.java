package com.dazong.common.mq.core.consumer;

import com.dazong.common.mq.domian.Message;

/**
 * @author huqichao
 * @date 2017-11-02 14:04
 **/
public interface IMessageListener {

    /**
     * 监听mq接收到消息后的处理
     * @param message mq消息
     */
    void receive(Message message);
}
