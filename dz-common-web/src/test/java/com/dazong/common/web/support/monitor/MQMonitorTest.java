package com.dazong.common.web.support.monitor;

import org.junit.Test;
import org.springframework.jms.core.JmsTemplate;

/**
 * @author manson.zhou on 2018/1/23.
 */
public class MQMonitorTest {

    @Test
    public void check() {
        JmsTemplate jmsTemplate = new JmsTemplate();
        MQMonitor mqMonitor = new MQMonitor(jmsTemplate);
        mqMonitor.check();
    }
}
