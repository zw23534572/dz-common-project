package com.dazong.common.mq.core.consumer.activemq;

import com.dazong.common.mq.constant.Constants;
import com.dazong.common.mq.constant.SubscribeType;
import com.dazong.common.mq.core.consumer.AbstractConsumer;
import com.dazong.common.mq.core.consumer.IMessageListener;
import com.dazong.common.mq.dao.mapper.MQMessageMapper;
import com.dazong.common.mq.domian.Consumer;
import com.dazong.common.mq.manager.MQNotifyManager;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.*;
import java.util.Map;

/**
 * @author huqichao
 * @create 2017-11-03 09:32
 **/
public class ActiveMQConsumer extends AbstractConsumer {

    private JmsTemplate jmsTemplate;

    private MQNotifyManager notifyManager;

    private MQMessageMapper messageMapper;

    public ActiveMQConsumer(JmsTemplate jmsTemplate, MQNotifyManager notifyManager, MQMessageMapper messageMapper){
        this.jmsTemplate = jmsTemplate;
        this.notifyManager = notifyManager;
        this.messageMapper = messageMapper;
    }

    public void init() throws JMSException {
        Connection connection = jmsTemplate.getConnectionFactory().createConnection();
        connection.start();

        // Create a Session
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

        Map<Consumer, IMessageListener> listenerMap = notifyManager.getListenerMap();
        for (Map.Entry<Consumer, IMessageListener> entry : listenerMap.entrySet()) {
            Consumer consumer = entry.getKey();
            if (consumer.getType().equals(SubscribeType.ACTIVEMQ)){
                String queueName;
                if (consumer.isQueue()){
                    queueName = consumer.getDestination();
                } else {
                    queueName = Constants.CONSUMER_PREFIX + consumer.getName() + "." + Constants.TOPIC_PREFIX + consumer.getDestination();
                }
                Destination destination = session.createQueue(queueName);
                MessageConsumer messageConsumer = session.createConsumer(destination);
                messageConsumer.setMessageListener(new ActiveMQListener(session, messageMapper, notifyManager, entry.getValue(), consumer));
            }
        }
    }
}
