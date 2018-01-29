package com.dazong.common.web.support.monitor;

import com.alibaba.dubbo.rpc.service.EchoService;
import com.dazong.common.monitor.BaseMonitor;
import com.dazong.common.monitor.CheckResult;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

/**
 * @author manson.zhou on 2018/1/23.
 */
public class DubboMonitorTest {

    public static final int ERROR = 500;
    public static final int SUCCESS = 200;


    @Test
    public void testOK() {
        EchoService echoServiceOK = new EchoService() {
            @Override
            public Object $echo(Object o) {
                return "OK";
            }
        };
        Map<EchoService, String> dubboConsumerMap = new HashMap<>();
        dubboConsumerMap.put(echoServiceOK, "test1");


        DubboMonitor dubboMonitor = new DubboMonitor(dubboConsumerMap);
        CheckResult checkResult = dubboMonitor.check();
        assertEquals(checkResult.getStatus(), SUCCESS);

    }

    @Test
    public void testERROR() {

        EchoService echoServiceError = new EchoService() {
            @Override
            public Object $echo(Object o) {
                return "ERROR";
            }
        };
        Map<EchoService, String> dubboConsumerMap = new HashMap<>();
        dubboConsumerMap.put(echoServiceError, "test2");

        DubboMonitor dubboMonitor = new DubboMonitor(dubboConsumerMap);
        CheckResult checkResult = dubboMonitor.check();
        assertEquals(checkResult.getStatus(), ERROR);
    }

}
