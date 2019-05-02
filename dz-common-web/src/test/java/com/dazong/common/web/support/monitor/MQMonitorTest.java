package com.dazong.common.web.support.monitor;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author manson.zhou on 2018/1/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:hsql-test-spring-config.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class MQMonitorTest {

    @Autowired
    JmsTemplate jmsTemplate;

    @Test
    public void check() {
        MQMonitor mqMonitor = new MQMonitor(jmsTemplate);
        mqMonitor.check();
    }
}
