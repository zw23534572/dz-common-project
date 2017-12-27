package com.dazong.common.mq.rabbitmq;

public interface IListener {

    void process(String message);
}
