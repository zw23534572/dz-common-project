package com.dazong.example.web.configurer;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author huqichao
 */
@Configuration
public class ElasticJobConfigurer {

    @Resource
    private ZookeeperRegistryCenter regCenter;

    @Bean(initMethod = "init", destroyMethod = "close")
    public ZookeeperRegistryCenter regCenter(@Value("${zk.host}") final String serverList,
                                             @Value("${elastic.job.zk.namespace}") final String namespace) {
        ZookeeperConfiguration configuration = new ZookeeperConfiguration(serverList, namespace);
        configuration.setConnectionTimeoutMilliseconds(3000);
        configuration.setSessionTimeoutMilliseconds(3000);
        return new ZookeeperRegistryCenter(configuration);
    }

    //@Bean(initMethod = "init")
    //public JobScheduler registryRefundProcessJob(RefundProcessJob refundProcessJob) {
    //    LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(
    //            JobCoreConfiguration.newBuilder(RefundProcessJob.class.getSimpleName(), "0 0/5 * * * ?", 1).build(),
    //            RefundProcessJob.class.getCanonicalName())).overwrite(true).build();
    //    return new SpringJobScheduler(refundProcessJob, regCenter, liteJobConfiguration);
    //}
}
