package com.dazong.common.web.monitor;

import org.springframework.jms.core.JmsTemplate;

import com.dazong.common.monitor.CheckResult;
import com.dazong.common.monitor.Monitor;

/**
 * Created by huqichao on 17/5/25.
 */
public class MQMonitor extends Monitor {


    public MQMonitor(Object obj) {
        super(obj);
    }

    /**
     * 检测
     *
     * @return 检测结果
     */
    @Override
    public CheckResult check() {
        CheckResult result = new CheckResult(NAME_MQ);
        JmsTemplate jmsTemplate = (JmsTemplate) obj;
        if(jmsTemplate != null){
            try {
                jmsTemplate.setReceiveTimeout(1000);
                jmsTemplate.receive("monitor test");
            } catch (Exception e) {
                logger.error("mq connection error", e);
                result.setSuccess(false);
                result.setStatus(ERROR);
                result.setErrorMsg("mq connection error");
            }
        }
        return result;
    }
}
