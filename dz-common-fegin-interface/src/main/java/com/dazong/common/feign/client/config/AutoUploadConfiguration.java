package com.dazong.common.feign.client.config;

import com.dazong.common.feign.client.api.IUploadService;
import com.dazong.common.feign.client.utils.UploadUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author lori.li
 */
@Configuration
@EnableFeignClients(clients = {IUploadService.class})
@Import(UploadUtils.class)
public class AutoUploadConfiguration {
}
