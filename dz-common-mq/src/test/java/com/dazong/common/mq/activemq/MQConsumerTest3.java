package com.dazong.common.mq.activemq;

import com.dazong.common.mq.annotation.Subscribe;
import com.dazong.common.mq.core.consumer.IMessageListener;
import com.dazong.common.mq.domian.Message;

/**
 * @author huqichao
 * @date 2018-01-19 15:27
 **/
@Subscribe(queue = "mq.test")
public class MQConsumerTest3 implements IMessageListener {
    @Override
    public void receive(Message message) {
        System.out.println(message.getBody());
        message.acknowledge();
    }
}
