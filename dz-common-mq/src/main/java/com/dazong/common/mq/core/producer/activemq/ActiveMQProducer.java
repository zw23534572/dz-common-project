package com.dazong.common.mq.core.producer.activemq;

import com.dazong.eye.api.RpcPoint;
import com.dazong.eye.api.RpcPointSupport;
import com.dazong.eye.api.TraceContext;
import com.dazong.common.mq.dao.mapper.MQMessageMapper;
import com.dazong.common.mq.domian.DZMessage;
import com.dazong.common.mq.core.producer.AbstractProducer;
import com.dazong.common.mq.exception.MQException;
import com.dazong.common.mq.manager.MQSendManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.UUID;

/**
 * @author huqichao
 * @date 2017-10-30 15:32
 **/
@Component
public class ActiveMQProducer extends AbstractProducer {

    @Autowired
    private MQMessageMapper messageMapper;

    @Autowired
    private MQSendManager sendManager;

    @Override
    public void sendMessage(DZMessage message) throws MQException {
        try {
            Assert.notNull(message, "消息体不能空");
            Assert.notNull(message.getBody(), "消息内容不能空");
            Assert.notNull(message.getDestination(), "消息主题不能空");
            message.setEventId(UUID.randomUUID().toString());
            message.setStatus(DZMessage.STATUS_DOING);
            message.setSendTime(System.currentTimeMillis());

            RpcPoint point = TraceContext.currentPoint();
            if (point != null){
                message.setAttachment(RpcPointSupport.serializeForTransfer(point));
            }

            messageMapper.insertMessage(message);

            sendManager.send(message);
        } catch (Exception e) {
            throw new MQException(e);
        }
    }
}
