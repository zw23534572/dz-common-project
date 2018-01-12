package com.dazong.common.feign.client.config;

import com.dazong.common.feign.client.api.IUpService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author lori.li
 */
@Configuration
@ConditionalOnProperty(prefix = "feignclient", value = {"up.serviceId", "up.url"})
@EnableFeignClients(clients = {IUpService.class})
public class AutoUpConfiguration {
}
