package com.dazong.common.mq.core.consumer;

import com.dazong.common.mq.domian.Message;

/**
 * @author huqichao
 * @create 2017-11-02 14:04
 **/
public interface IMessageListener {

    void receive(Message message);
}
