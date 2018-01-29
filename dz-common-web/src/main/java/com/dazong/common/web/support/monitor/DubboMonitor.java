package com.dazong.common.web.support.monitor;

import com.alibaba.dubbo.rpc.service.EchoService;
import com.dazong.common.monitor.BaseMonitor;
import com.dazong.common.monitor.CheckResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author huqichao Created by on 17/5/25.
 */
public class DubboMonitor extends BaseMonitor {

    public DubboMonitor(Object obj) {
        super(obj);
    }

    /**
     * 检测
     *
     * @return 检测结果
     */
    @Override
    @SuppressWarnings("unchecked")
    public CheckResult check() {
        CheckResult result = new CheckResult(NAME_DUBBO);
        Map<EchoService, String> dubboConsumerMap = (Map<EchoService, String>) obj;
        List<String> problemServiceList = new ArrayList<>();
        if (dubboConsumerMap != null && !dubboConsumerMap.isEmpty()) {
            for (Map.Entry<EchoService, String> entry : dubboConsumerMap.entrySet()) {
                try {
                    // 回声测试可用性
                    Object status = entry.getKey().$echo("OK");
                    if (!"OK".equals(status)) {
                        problemServiceList.add(entry.getValue());
                    }
                } catch (Exception e) {
                    problemServiceList.add(entry.getValue());
                }
            }
        }
        if (problemServiceList.size() > 0) {
            result.setSuccess(false);
            result.setStatus(ERROR);
            result.setErrorMsg(problemServiceList.toString());
        }
        return result;
    }
}
