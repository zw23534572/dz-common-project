package com.dazong.common.mq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author huqichao
 * @date 2017-11-10 16:21
 **/
@Configuration
@EnableAsync
public class MQThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {
    @Bean
    public AsyncTaskExecutor mqTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("MQ-Executor-");
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(50);
        return executor;
    }
}
