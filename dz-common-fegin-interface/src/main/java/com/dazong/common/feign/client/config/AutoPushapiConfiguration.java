package com.dazong.common.feign.client.config;

import com.dazong.common.feign.client.api.IPushapiService;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 *  @author yanghui
 */
@Configuration
@EnableFeignClients(clients={IPushapiService.class})
public class AutoPushapiConfiguration {

}
