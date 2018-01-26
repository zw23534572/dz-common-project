package com.dazong.common.web.support.monitor;

import org.junit.Test;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author manson.zhou on 2018/1/23.
 */
public class RedisMonitorTest {
    @Test
    public void check() {
        RedisTemplate redisTemplate = new RedisTemplate();
        RedisMonitor monitor = new RedisMonitor(redisTemplate);
        monitor.check();
    }
}
