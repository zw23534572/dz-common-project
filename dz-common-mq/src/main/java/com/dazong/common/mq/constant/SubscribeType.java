package com.dazong.common.mq.constant;

/**
 * @author huqichao
 * @date 2017-11-06 14:27
 **/
public enum SubscribeType {

    /**
     * 订阅activemq消息
     */
    ACTIVEMQ("activemq");

    private String type;
    private SubscribeType(String type){
        this.type = type;
    }
}
