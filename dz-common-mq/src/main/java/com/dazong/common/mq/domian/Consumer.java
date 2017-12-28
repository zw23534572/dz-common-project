package com.dazong.common.mq.domian;


import com.dazong.common.mq.annotation.Subscribe;
import com.dazong.common.mq.constant.SubscribeType;
import com.dazong.common.mq.exception.MQException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper=true)
public class Consumer {

    private String destination;

    private String name;

    private SubscribeType type;

    private boolean queue;

    public static Consumer create(Subscribe subscribe, Class clazz){
        Consumer consumer = new Consumer();
        consumer.setType(subscribe.type());
        if (subscribe.queue().length() > 0){
            consumer.setQueue(true);
            consumer.setDestination(subscribe.queue());
        } else if (subscribe.topic().length() > 0){
            consumer.setDestination(subscribe.topic());
        } else {
            throw new MQException("%s not have subscribe queue/topic", clazz.getSimpleName());
        }

        if (subscribe.name().length() > 0){
            consumer.setName(subscribe.name());
        } else {
            consumer.setName(clazz.getSimpleName());
        }

        return consumer;
    }
}
