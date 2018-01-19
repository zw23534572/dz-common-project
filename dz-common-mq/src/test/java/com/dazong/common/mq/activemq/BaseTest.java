package com.dazong.common.mq.activemq;

import com.dazong.common.mq.core.producer.activemq.ActiveMQProducer;
import com.dazong.common.mq.domian.DZMessage;
import org.apache.activemq.broker.BrokerService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author huqichao
 * @date 2018-01-19 15:22
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:hsql-test-spring-config.xml"})
public class BaseTest {

    @Autowired
    private ActiveMQProducer producer;

    @BeforeClass
    public static void init(){
        BrokerService broker = new BrokerService();

        // configure the broker
        try {
            broker.addConnector("tcp://localhost:61616");
            broker.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void send(){
        DZMessage message = DZMessage.wrap("mq.test", "卡上打上客户端");
        producer.sendMessage(message);
    }
}
