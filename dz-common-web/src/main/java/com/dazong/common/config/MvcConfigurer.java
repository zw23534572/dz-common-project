package com.dazong.common.config;


import com.dazong.common.support.JsonHttpMessageConverter;
import com.dazong.common.support.JsonMappingExceptionResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Spring MVC 配置
 */
@Configuration
public class MvcConfigurer extends WebMvcConfigurerAdapter {

    /**
     * 消息返回时，统一处理
     * 1.有fastjson进行处理，日期格式化等等
     * 2.封装成公司的包装类commonResponse
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        JsonHttpMessageConverter converter = new JsonHttpMessageConverter();
        converter.init();
        converters.add(converter);
    }


    /**
     * 统一异常处理
     */
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(new JsonMappingExceptionResolver());
    }

}
