package com.dazong.common.mq.manager;

import com.dazong.common.mq.core.consumer.IMessageListener;
import com.dazong.common.mq.dao.mapper.MQMessageMapper;
import com.dazong.common.mq.domian.Consumer;
import com.dazong.common.mq.domian.DZConsumerMessage;
import com.dazong.common.mq.domian.DZMessage;
import com.dazong.common.mq.exception.MQException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author huqichao
 * @date 2017-10-31 09:47
 **/
@Component
public class MQNotifyManager {

    private Logger logger = LoggerFactory.getLogger(MQNotifyManager.class);

    @Autowired
    private MQMessageMapper messageMapper;

    private Map<Consumer, IMessageListener> listenerMap = new HashMap<>();

    @Async("mqTaskExecutor")
    public void notifyMessage(final DZConsumerMessage message){
        IMessageListener listener = null;
        try {
            logger.debug("通知消息------>{}", message);
            for (Map.Entry<Consumer, IMessageListener> entry : listenerMap.entrySet()){
                //消息为队列时，则只判断消息目标。消息为topic时，判断消息目标和名称
                if (entry.getKey().getDestination().equals(message.getDestination())
                        && isMatchSubscribeNameOrQueue(entry.getKey(), message)){
                    listener = entry.getValue();
                    break;
                }
            }
            if (listener == null){
                logger.warn("没有 Listener 监听 {} 消息", message);
                messageMapper.updateConsumerMessageStatusById(message.getId(), DZMessage.STATUS_DONE);
                return;
            }

            notify(listener, message, message.getAttachment());
        } catch (Exception e) {
            throw new MQException(e, "消息处理失败: %s", message);
        } finally {
            if (listener != null){
                messageMapper.updateConsumerMessage(message);
            }
        }
    }

    private boolean isMatchSubscribeNameOrQueue(Consumer consumer, DZConsumerMessage message){
        return message.isQueue() || consumer.getName().equals(message.getName());
    }

    @Async("mqTaskExecutor")
    public void notifyMessage(final IMessageListener listener, final DZConsumerMessage message){
        try {
            logger.debug("通知消息------>{}", message);
            notify(listener, message, message.getAttachment());
        } catch (Exception e) {
            throw new MQException(e, "消息处理失败: %s", message);
        } finally {
            messageMapper.updateConsumerMessage(message);
        }
    }

    /**
     * 消息通知具体业务处理
     * @param listener 消息具体业务处理类
     * @param message 消息
     * @param attachment 消息中附件，飞马眼使用
     * @throws Exception
     */
    private void notify(IMessageListener listener, DZConsumerMessage message, String attachment) {
        listener.receive(message.copy(messageMapper));
    }


    public void registerListener(Consumer consumer, IMessageListener listener) {
        for (Map.Entry<Consumer, IMessageListener> entry : listenerMap.entrySet()){
            if (consumer.getName().equals(entry.getKey().getName())){
                throw new MQException("Consumer name must be unique: [%s],[%s]", entry.getValue().getClass().getName(), listener.getClass().getName());
            }
        }
        listenerMap.put(consumer, listener);
    }

    public Map<Consumer, IMessageListener> getListenerMap() {
        return listenerMap;
    }
}
