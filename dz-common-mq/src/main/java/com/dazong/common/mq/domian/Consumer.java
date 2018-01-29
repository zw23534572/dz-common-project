package com.dazong.common.mq.domian;


import com.dazong.common.mq.annotation.Subscribe;
import com.dazong.common.mq.constant.SubscribeType;
import com.dazong.common.mq.exception.MQException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.core.env.Environment;

/**
 * @author huqichao
 * @date 2017-10-30 15:31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper=true)
public class Consumer {

    private String destination;

    private String name;

    private SubscribeType type;

    private boolean queue;

    private int notifyMaxCount;

    public static Consumer create(Subscribe subscribe, Class clazz, Environment env){
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

        if (subscribe.notifyMaxCount() > -1 && subscribe.notifyMaxCountPropKey().length() == 0){
            consumer.setNotifyMaxCount(subscribe.notifyMaxCount());
        } else if (subscribe.notifyMaxCount() == -1 && subscribe.notifyMaxCountPropKey().length() > 0){
            String countStr = env.getProperty(subscribe.notifyMaxCountPropKey());
            if (countStr == null){
                throw new MQException("could not resolve placeholder %s", subscribe.notifyMaxCountPropKey());
            }
            int count = Integer.valueOf(countStr);
            consumer.setNotifyMaxCount(count);
        } else if (subscribe.notifyMaxCount() > -1 && subscribe.notifyMaxCountPropKey().length() > 0){
            String countStr = env.getProperty(subscribe.notifyMaxCountPropKey());
            if (countStr == null){
                throw new MQException("could not resolve placeholder %s", subscribe.notifyMaxCountPropKey());
            }
            int count = Integer.valueOf(countStr);
            consumer.setNotifyMaxCount(subscribe.notifyMaxCount() > count ? subscribe.notifyMaxCount() : count);
        }
        if (consumer.getNotifyMaxCount() == 1){
            throw new MQException("notifyMaxCount must be more then 1", subscribe.notifyMaxCountPropKey());
        }
        return consumer;
    }
}
