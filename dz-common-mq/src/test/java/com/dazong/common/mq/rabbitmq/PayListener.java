package com.dazong.common.mq.rabbitmq;

public class PayListener implements IListener {

    public void process(String message) {
        System.out.println("付款----" + message);
    }
}
