package com.dazong.common.feign.client.config;

import com.dazong.common.feign.client.api.IPushapiService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 *  @author yanghui
 */
@Configuration
@ConditionalOnProperty(prefix = "feignclient",value = {"pushapi.serviceId","pushapi.url"})
@EnableFeignClients(clients={IPushapiService.class})
public class AutoPushapiConfiguration {

}
