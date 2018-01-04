package com.dazong.common.feign.client.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

import com.dazong.common.feign.client.api.IAuthService;

/**
 *  @author yanghui
 */
@Configuration
@ConditionalOnProperty(prefix = "feignclient",value = {"auth.serviceId","auth.url"})
@EnableFeignClients(clients={IAuthService.class})
public class AutoAuthConfiguration {

}
