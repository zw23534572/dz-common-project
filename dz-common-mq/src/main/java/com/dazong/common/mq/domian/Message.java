package com.dazong.common.mq.domian;

import com.dazong.common.mq.dao.mapper.MQMessageMapper;
import com.dazong.common.mq.exception.MQException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author huqichao
 * @date 2017-11-06 15:30
 **/
public class Message {

    private Logger logger = LoggerFactory.getLogger(Message.class);

    protected MQMessageMapper messageMapper;

    protected Long id;

    protected String body;

    public String getBody(){
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void acknowledge(){
        try {
            messageMapper.updateConsumerMessageStatusById(id, DZConsumerMessage.STATUS_DONE);
            logger.debug("ack event id: {}", id);
        } catch (Exception e) {
            throw new MQException("ack fail: %s", id);
        }
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageMapper=" + messageMapper +
                ", id=" + id +
                ", body='" + body + '\'' +
                '}';
    }
}
