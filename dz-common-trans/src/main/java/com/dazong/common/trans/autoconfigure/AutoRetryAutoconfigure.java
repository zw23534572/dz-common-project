package com.dazong.common.trans.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.dazong.common.trans.annotation.EnableAutoRetry;
import com.dazong.common.trans.properties.DzTransactionProperties;

/**
 * spring boot配置类
 * @author hujunzhong
 *
 */
@Configuration
@ConditionalOnBean(annotation={EnableAutoRetry.class})
@EnableConfigurationProperties(DzTransactionProperties.class)
@ImportResource("classpath:META-INF/spring/spring-dz-transaction-config.xml")
public class AutoRetryAutoconfigure {

}
