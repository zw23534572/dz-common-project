package com.dazong.common.web.support.monitor;

import com.alibaba.dubbo.rpc.service.EchoService;
import com.dazong.common.monitor.CheckResult;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author manson.zhou on 2018/1/23.
 */
public class DubboMonitorTest {

    @Test
    public void test() {
        Map<EchoService, String> dubboConsumerMap = new HashMap<>();


        EchoService echoServiceOK = new EchoService() {
            @Override
            public Object $echo(Object o) {
                return "OK";
            }
        };

        EchoService echoServiceError = new EchoService() {
            @Override
            public Object $echo(Object o) {
                return "ERROR";
            }
        };

        dubboConsumerMap.put(echoServiceOK, "test1");
        dubboConsumerMap.put(echoServiceError, "test2");


        DubboMonitor dubboMonitor = new DubboMonitor(dubboConsumerMap);
        CheckResult checkResult = dubboMonitor.check();
        System.out.print("CheckResult:" + checkResult);

    }
}
