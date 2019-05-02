package com.dazong.common.web.support.monitor;

import org.junit.Test;

/**
 * @author manson.zhou on 2018/1/23.
 */
public class VersionMonitorTest {

    @Test
    public void checkTest() {
        VersionMonitor versionMonitor = new VersionMonitor(new Object());
        versionMonitor.check();
    }
}
