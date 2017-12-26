package com.dazong.common.web.monitor;

import com.alibaba.dubbo.rpc.service.EchoService;
import com.dazong.common.monitor.CheckResult;
import com.dazong.common.monitor.Monitor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by huqichao on 17/5/25.
 */
public class DubboMonitor extends Monitor {

    public DubboMonitor(Object obj) {
        super(obj);
    }

    /**
     * 检测
     * @return 检测结果
     */
    @Override
    @SuppressWarnings("unchecked")
    public CheckResult check() {
        CheckResult result = new CheckResult(NAME_DUBBO);
        Map<EchoService, String> dubboConsumerMap = (Map<EchoService, String>)obj;
        List<String> problemServiceList = new ArrayList<>();
        if(dubboConsumerMap != null && !dubboConsumerMap.isEmpty()){
            for(Map.Entry<EchoService, String> entry : dubboConsumerMap.entrySet()){
                try {
                    Object status = entry.getKey().$echo("OK"); // 回声测试可用性
                    if(!status.equals("OK")){
                        problemServiceList.add(entry.getValue());
                    }
                } catch (Exception e) {
                    problemServiceList.add(entry.getValue());
                }
            }
        }
        if(problemServiceList.size() > 0){
            result.setSuccess(false);
            result.setStatus(ERROR);
            result.setErrorMsg(problemServiceList.toString());
        }
        return result;
    }
}
