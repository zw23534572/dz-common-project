package com.dazong.example.web.controller;

import com.dazong.common.mq.core.producer.activemq.ActiveMQProducer;
import com.dazong.common.mq.domian.DZMessage;
import com.dazong.example.common.constant.MQConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huqichao
 * @date 2018-01-08 10:19
 */
@RestController
@RequestMapping("/mq/noUser")
public class MQController {

    @Autowired
    private ActiveMQProducer producer;

    /**
     * 发送mq消息
     * @param content 消息内容
     * @return 返回状态
     */
    @RequestMapping("/send")
    public String sendMQ(String content){
        DZMessage message = DZMessage.wrap(MQConstant.MQ_DESTINATION, content);
        //立即发送mq通知给业务
        message.setImmediate(true);
        //不是队列
        message.setQueue(false);
        //不是发送第三方
        message.setSendThird(false);
        producer.sendMessage(message);
        return "SCCUESS";
    }
}
