package com.dazong.example.web.web.service.mq;

import com.dazong.common.mq.annotation.Subscribe;
import com.dazong.common.mq.constant.SubscribeType;
import com.dazong.common.mq.core.consumer.IMessageListener;
import com.dazong.common.mq.domian.Message;
import com.dazong.example.common.constant.MQConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author huqichao
 * @date 2017-11-04 16:15
 **/
@Subscribe(topic = MQConstant.MQ_DESTINATION, name = "Turn", type = SubscribeType.ACTIVEMQ)
public class TurnMQ implements IMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(PayMQ.class);

    @Override
    public void receive(Message message) {
        logger.debug("TurnMQ-------{}", message.getBody());
        message.acknowledge();
    }
}
