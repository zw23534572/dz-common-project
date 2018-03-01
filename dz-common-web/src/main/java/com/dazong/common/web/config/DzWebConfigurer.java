package com.dazong.common.web.config;


import com.dazong.common.web.annotation.EnableDzWeb;
import com.dazong.common.web.support.jsonConverter.JsonHttpMessageConverter;
import com.dazong.common.web.support.jsonConverter.JsonMappingExceptionResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Spring MVC 配置
 */
@Configuration
@ConditionalOnBean(annotation = {EnableDzWeb.class})
public class DzWebConfigurer extends WebMvcConfigurerAdapter {

    protected static Logger logger = LoggerFactory.getLogger(DzWebConfigurer.class);

    /**
     * json消息转换类
     * @return
     */
    @Bean
    public JsonHttpMessageConverter jsonHttpMessageConverter() {
       return new JsonHttpMessageConverter();
    }

    /**
     * 消息返回时，统一处理
     * 1.有fastjson进行处理，日期格式化等等
     * 2.封装成公司的包装类commonResponse
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        logger.info("MvcConfigurer configureMessageConverters started");
        converters.add(jsonHttpMessageConverter());
    }


    /**
     * 统一异常处理
     */
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        logger.info("MvcConfigurer configureHandlerExceptionResolvers started");
        exceptionResolvers.add(new JsonMappingExceptionResolver());
    }

}
