package com.dazong.common.mq.activemq;

import com.dazong.common.mq.constant.Constants;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivemqSend {

    public static void main(String[] args) throws Exception {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://master:61616");
//        JmsConnectionFactory connectionFactory = new JmsConnectionFactory("amqp://master:5672");

        // Create a Connection
        Connection connection = connectionFactory.createConnection();
        connection.start();

        // Create a Session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create the destination (Topic or Queue)
//        Destination destination = session.createQueue("test11");
        Destination destination = session.createTopic(Constants.TOPIC_PREFIX + "test11");

        // Create a MessageProducer from the Session to the Topic or Queue
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);

        // Create a messages
        String text = "Hello world! From: " + Thread.currentThread().getName() + " : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
        TextMessage message = session.createTextMessage(text);

        // Tell the producer to send the message
        System.out.println("Sent message: "+ message.getText() + " : " + Thread.currentThread().getName());
        producer.send(message);

        // Clean up
        session.close();
        connection.close();
    }
}
