package com.dazong.common.feign.client.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

import com.dazong.common.feign.client.api.IPushapiService;

/**
 *  @author yanghui
 */
@Configuration
@ConditionalOnProperty(prefix = "feignclient",value = {"pushapi.serviceId","pushapi.url"})
@EnableFeignClients(clients={IPushapiService.class})
public class AutoPushapiConfiguration {

}
