package com.dazong.common.feign.client.config;

import com.dazong.common.feign.client.api.IUserInfoService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 *  @author yanghui
 */
@Configuration
@EnableFeignClients(clients={IUserInfoService.class})
public class AutoUserapiConfiguration {

}
