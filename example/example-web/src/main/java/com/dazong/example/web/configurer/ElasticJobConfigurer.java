package com.dazong.example.web.configurer;


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

}
