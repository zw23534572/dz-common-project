package com.dazong.common.support.monitor;

import com.alibaba.dubbo.common.utils.NetUtils;
import com.dazong.common.monitor.BaseMonitor;
import com.dazong.common.monitor.CheckResult;

import java.util.Properties;

/**
 * 
 * @author huqichao
 * Created by  on 17/5/25.
 *
 */
public class VersionMonitor extends BaseMonitor {


    public VersionMonitor(Object obj) {
        super(obj);
    }

    /**
     * 检测
     *
     * @return 检测结果
     */
    @Override
    public CheckResult check() {
        final String ip = NetUtils.getLocalAddress().getHostAddress();
        final String deptName = System.getProperty("fmeye.deptName");
        final String appName = System.getProperty("fmeye.appName");

        CheckResult result = new CheckResult(NAME_VERSION);
        try {
            Properties properties = new Properties();
            properties.load(VersionMonitor.class.getResourceAsStream("/META-INF/maven/com.dazong.eye/fmeye-common/pom.properties"));
            String version = properties.getProperty("version");
            result.setErrorMsg(ip + "," + deptName + "," + appName + "," + version);
        } catch (Exception e) {
            logger.error("VersionMonitor", e);
            result.setSuccess(false);
            result.setStatus(ERROR);
        }

        return result;
    }
}
