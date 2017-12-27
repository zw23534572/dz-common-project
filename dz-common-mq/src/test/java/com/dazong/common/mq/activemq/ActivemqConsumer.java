package com.dazong.common.mq.activemq;

import com.dazong.common.mq.constant.Constants;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.qpid.jms.JmsConnectionFactory;

import javax.jms.*;

public class ActivemqConsumer {

    public static void main(String[] args) throws Exception {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://master:61616");
//        JmsConnectionFactory connectionFactory = new JmsConnectionFactory("amqp://master:5672");

        // Create a Connection
        Connection connection = connectionFactory.createConnection();
        connection.start();

        RedeliveryPolicy policy = connectionFactory.getRedeliveryPolicy();
//        policy.setInitialRedeliveryDelay(500);
        policy.setBackOffMultiplier(2);
//        policy.setUseExponentialBackOff(true);
        policy.setMaximumRedeliveries(-1);

//        connection.setExceptionListener(new ExceptionListener() {
//            @Override
//            public void onException(JMSException e) {
//                System.out.println("JMS Exception occured.  Shutting down client.");
//            }
//        });

        // Create a Session
        final Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        // Create the destination (Topic or Queue)
        Destination destination = session.createQueue("Consumer.aaa." + Constants.TOPIC_PREFIX + "test11");

        // Create a MessageConsumer from the Session to the Topic or Queue
        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    String text = null;
                    try {
                        text = textMessage.getText();
                        System.out.println(System.currentTimeMillis() + "-------111Received: " + text + "----" + message.getJMSMessageID());
                        session.recover();
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        System.out.println(System.currentTimeMillis() + "------2222Received: " + message + "====" + message.getJMSMessageID());
                        session.recover();
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
//                try {
//                    message.acknowledge();
//                } catch (JMSException e) {
//                    e.printStackTrace();
//                }
            }
        });
//        consumer.close();
//        session.close();
//        connection.close();
    }

}
