package com.dazong.common.mq.domian;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author huqichao
 * @create 2017-10-30 15:58
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper=true)
public class DZMessage {

    public static final int STATUS_DOING = 0;
    public static final int STATUS_DONE = 1;

    @JSONField(serialize = false)
    private Long id;

    private String eventId;

    private String groupId;

    private boolean immediate;

    private String body;

    @JSONField(serialize = false)
    private int status;

    @JSONField(serialize = false)
    private String destination;

    private boolean queue;

    /**该消息是否发送第三方，如果是第三方只会发送body里的内容*/
    private boolean sendThird;

    /**接收到发送消息的时间*/
    private Long sendTime;

    private String attachment;

    public DZMessage(){
    }

    public DZMessage(String destination, String body){
        this.destination = destination;
        this.body = body;
    }

    public DZMessage(String destination, String body, String groupId){
        this.destination = destination;
        this.body = body;
        this.groupId = groupId;
    }

    public static DZMessage wrap(String destination, String body) {
        return new DZMessage(destination, body);
    }

    public static DZMessage wrap(String destination, String body, String groupId) {
        return new DZMessage(destination, body, groupId);
    }
}
