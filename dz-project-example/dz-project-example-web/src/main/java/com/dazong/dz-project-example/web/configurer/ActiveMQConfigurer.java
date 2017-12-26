package com.dazong.dz-project-example.web.configurer;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.DeliveryMode;

/**
 * mq 配置
 * @author huqichao
 */
@Configuration
public class ActiveMQConfigurer {

    @Bean
    public RedeliveryPolicy redeliveryPolicy(){
        //该策略表示，消息失败后，每个1、2、4、8、16、32、64....再次发送消息
        RedeliveryPolicy policy = new RedeliveryPolicy();
        policy.setBackOffMultiplier(2);
        policy.setMaximumRedeliveries(-1);
        return policy;
    }

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory(@Value("${spring.activemq.broker-url}")String url, RedeliveryPolicy redeliveryPolicy){
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(url);
        activeMQConnectionFactory.setRedeliveryPolicy(redeliveryPolicy);
        return activeMQConnectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate(ActiveMQConnectionFactory activeMQConnectionFactory){
        JmsTemplate jmsTemplate = new JmsTemplate();
        //进行持久化配置 1表示非持久化，2表示持久化
        jmsTemplate.setDeliveryMode(DeliveryMode.PERSISTENT);
        jmsTemplate.setConnectionFactory(activeMQConnectionFactory);
        return jmsTemplate;
    }
}
