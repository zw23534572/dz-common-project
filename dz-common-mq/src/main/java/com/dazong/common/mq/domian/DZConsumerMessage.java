package com.dazong.common.mq.domian;

import com.alibaba.fastjson.annotation.JSONField;
import com.dazong.common.mq.dao.mapper.MQMessageMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * @author huqichao
 * @date 2017-10-30 15:58
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper=true)
public class DZConsumerMessage {

    public static final int STATUS_DOING = 0;
    public static final int STATUS_DONE = 1;

    @JSONField(serialize = false)
    private Long id;

    private String name;

    private String eventId;

    private String groupId;

    private String body;

    @JSONField(serialize = false)
    private int status;

    @JSONField(serialize = false)
    private String destination;

    /**接收到发送消息的时间*/
    private Long sendTime;

    /**接收到消息的时间*/
    private Long receiveTime;

    private Integer notifyCount;

    private Date lastNotifyTime;

    private boolean queue;

    private String attachment;

    public DZConsumerMessage(){
    }

    public DZConsumerMessage(DZMessage message){
        this.destination = message.getDestination();
        this.body = message.getBody();
        this.eventId = message.getEventId();
        this.groupId = message.getGroupId();
        this.sendTime = message.getSendTime();
        this.queue = message.isQueue();
        this.notifyCount = 0;
        this.receiveTime = System.currentTimeMillis();
        this.attachment = message.getAttachment();
    }

    public Message copy(MQMessageMapper messageMapper){
        Message message = new Message();
        message.id = id;
        message.body = body;
        message.messageMapper = messageMapper;

        return message;
    }
}
