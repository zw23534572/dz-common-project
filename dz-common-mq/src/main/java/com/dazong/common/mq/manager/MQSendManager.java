package com.dazong.common.mq.manager;

import com.alibaba.fastjson.JSON;
import com.dazong.common.mq.constant.Constants;
import com.dazong.common.mq.dao.mapper.MQMessageMapper;
import com.dazong.common.mq.domian.DZMessage;
import com.dazong.common.mq.exception.MQException;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * @author huqichao
 * @date 2017-10-31 09:47
 **/
@Component
public class MQSendManager {

    private Logger logger = LoggerFactory.getLogger(MQSendManager.class);

    @Autowired
    private MQMessageMapper messageMapper;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Async("mqTaskExecutor")
    public void send(final DZMessage message){
        try {
            logger.debug("发送消息------>{}", message);
            jmsTemplate.setDeliveryMode(DeliveryMode.PERSISTENT);
            Destination destination;
            if (message.isQueue()){
                destination = new ActiveMQQueue(message.getDestination());
            } else {
                destination= new ActiveMQTopic(Constants.TOPIC_PREFIX + message.getDestination());
            }
            String json;
            if (message.isSendThird()){
                json = message.getBody();
            } else {
                json = JSON.toJSONString(message);
            }
            jmsTemplate.convertAndSend(destination, json);
            message.setStatus(DZMessage.STATUS_DONE);
            messageMapper.updateMessage(message);
        } catch (Exception e) {
            throw new MQException(e, "发送消息失败: ", message);
        }
    }
}
