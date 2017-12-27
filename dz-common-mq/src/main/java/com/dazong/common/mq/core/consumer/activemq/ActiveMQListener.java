package com.dazong.common.mq.core.consumer.activemq;

import com.alibaba.fastjson.JSON;
import com.dazong.common.mq.core.consumer.IMessageListener;
import com.dazong.common.mq.dao.mapper.MQMessageMapper;
import com.dazong.common.mq.domian.Consumer;
import com.dazong.common.mq.domian.DZConsumerMessage;
import com.dazong.common.mq.domian.DZMessage;
import com.dazong.common.mq.manager.MQNotifyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.util.List;

/**
 * @author huqichao
 * @create 2017-11-02 13:46
 **/
public class ActiveMQListener implements MessageListener {

    private Logger logger = LoggerFactory.getLogger(ActiveMQListener.class);

    private MQMessageMapper messageMapper;

    private MQNotifyManager notifyManager;

    private IMessageListener listener;

    private Consumer consumer;

    private Session session;

    public ActiveMQListener(Session session, MQMessageMapper messageMapper, MQNotifyManager notifyManager, IMessageListener listener, Consumer consumer){
        this.messageMapper = messageMapper;
        this.notifyManager = notifyManager;
        this.listener = listener;
        this.consumer = consumer;
        this.session = session;
    }

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            logger.debug("接收消息------>{}", textMessage.getText());
            DZMessage dzMessage = JSON.parseObject(textMessage.getText(), DZMessage.class);
            DZConsumerMessage consumerMessage = messageMapper.queryConsumerMessageByEventId(dzMessage.getEventId(), consumer.getName());
            if (consumerMessage != null){
                logger.debug("已有消费端消费该消息: {}", dzMessage.getEventId());
                message.acknowledge();
                return;
            }
            consumerMessage = new DZConsumerMessage(dzMessage);
            consumerMessage.setName(consumer.getName());
            consumerMessage.setDestination(consumer.getDestination());
            messageMapper.insertConsumerMessage(consumerMessage);
            //判断该消息是否立即通知，如果是，则判断除了当前消息，该消息组中是否还有未处理的消息
            if (dzMessage.isImmediate()){
                List<DZConsumerMessage> groupList = messageMapper.queryConsumerMessageByGroupId(consumerMessage.getGroupId(),
                        consumerMessage.getName(), DZConsumerMessage.STATUS_DOING);
                if (groupList.size() <= 1){
                    notifyManager.notifyMessage(listener, consumerMessage);
                }
            }
            message.acknowledge();
        } catch (Exception e) {
            try {
                session.recover();
            } catch (JMSException e1) {
                logger.error("接收消息失败-反馈", e1);
            }
            logger.error("接收消息失败", e);
        }
    }
}
