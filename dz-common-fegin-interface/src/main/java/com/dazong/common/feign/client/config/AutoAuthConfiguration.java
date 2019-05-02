package com.dazong.common.feign.client.config;

import com.dazong.common.feign.client.api.IAuthService;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 *  @author yanghui
 */
@Configuration
@EnableFeignClients(clients={IAuthService.class})
public class AutoAuthConfiguration {

}
