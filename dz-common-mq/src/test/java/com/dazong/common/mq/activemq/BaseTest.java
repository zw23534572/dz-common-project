package com.dazong.common.mq.activemq;

import com.dazong.common.mq.MQAutoConfiguration;
import com.dazong.common.mq.core.producer.activemq.ActiveMQProducer;
import com.dazong.common.mq.dao.mapper.MQMessageMapper;
import com.dazong.common.mq.domian.DZConsumerMessage;
import com.dazong.common.mq.domian.DZMessage;
import com.dazong.common.mq.domian.TableInfo;
import com.dazong.common.mq.job.ReTryNotifyJob;
import com.dazong.common.mq.manager.DBManager;
import org.apache.activemq.broker.BrokerService;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author huqichao
 * @date 2018-01-19 15:22
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:hsql-test-spring-config.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BaseTest {

    @Autowired
    private ActiveMQProducer producer;

    @Autowired
    private MQMessageMapper mqMessageMapper;

    @Autowired
    private DBManager dbManager;

    @Value("${db.name}")
    private String dbName;

    @Autowired
    private ReTryNotifyJob reTryNotifyJob;

    @BeforeClass
    public static void init(){
        BrokerService broker = new BrokerService();

        // configure the broker
        try {
            broker.setUseJmx(true);
            broker.setDataDirectory("./target/activemq-data");
            broker.addConnector("tcp://localhost:61616");
            broker.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void send(){
        sendQueue();
        sendTopic();
        sendTopicWithNonImmediate();
    }

    private void sendTopic(){
        DZMessage message = DZMessage.wrap("mq.test", "卡上打上客户端");
        message.setGroupId(UUID.randomUUID().toString());
        message.setImmediate(true);
        producer.sendMessage(message);

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<DZMessage> list = mqMessageMapper.queryMessageByStatus(DZMessage.STATUS_DONE, 10);
        System.out.println(list);

        assertThat(list.size()).isEqualTo(2).as("发送消息成功");

        List<DZConsumerMessage> list1 = mqMessageMapper.queryConsumerMessageByStatus(DZConsumerMessage.STATUS_DONE);
        System.out.println(list1);
        assertThat(list1.size()).isEqualTo(3).as("消费消息成功");

        try {
            TableInfo tableInfo = dbManager.selectTable(dbName, MQAutoConfiguration.TABLE_NAME);
            assertThat(tableInfo.getVersion()).isEqualTo(MQAutoConfiguration.SQL_VERSION).as("数据脚本更新成功");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void sendTopicWithNonImmediate(){
        DZMessage message = DZMessage.wrap("mq.test", "卡上打上客户端");
        message.setGroupId(UUID.randomUUID().toString());
        message.setImmediate(false);
        producer.sendMessage(message);

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        reTryNotifyJob.execute();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<DZMessage> list = mqMessageMapper.queryMessageByStatus(DZMessage.STATUS_DONE, 10);
        System.out.println(list);

        assertThat(list.size()).isEqualTo(3).as("发送消息成功");

        List<DZConsumerMessage> list1 = mqMessageMapper.queryConsumerMessageByStatus(DZConsumerMessage.STATUS_DONE);
        System.out.println(list1);
        assertThat(list1.size()).isEqualTo(5).as("消费消息成功");

        try {
            TableInfo tableInfo = dbManager.selectTable(dbName, MQAutoConfiguration.TABLE_NAME);
            assertThat(tableInfo.getVersion()).isEqualTo(MQAutoConfiguration.SQL_VERSION).as("数据脚本更新成功");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void sendQueue(){
        DZMessage message = DZMessage.wrap("mq.test", "卡上打上客户端");
        message.setGroupId(UUID.randomUUID().toString());
        message.setImmediate(true);
        message.setQueue(true);
        producer.sendMessage(message);

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<DZMessage> list = mqMessageMapper.queryMessageByStatus(DZMessage.STATUS_DONE, 10);
        System.out.println(list);

        assertThat(list.size()).isEqualTo(1).as("发送消息成功");

        List<DZConsumerMessage> list1 = mqMessageMapper.queryConsumerMessageByStatus(DZConsumerMessage.STATUS_DONE);
        System.out.println(list1);
        assertThat(list1.size()).isEqualTo(1).as("消费消息成功");

        try {
            TableInfo tableInfo = dbManager.selectTable(dbName, MQAutoConfiguration.TABLE_NAME);
            assertThat(tableInfo.getVersion()).isEqualTo(MQAutoConfiguration.SQL_VERSION).as("数据脚本更新成功");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
