package com.dazong.common.feign.client.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

import com.dazong.common.feign.client.api.IUserInfoService;

/**
 *  @author yanghui
 */
@Configuration
@ConditionalOnProperty(prefix = "feignclient",value = {"userapi.serviceId","userapi.url"})
@EnableFeignClients(clients={IUserInfoService.class})
public class AutoUserapiConfiguration {

}
