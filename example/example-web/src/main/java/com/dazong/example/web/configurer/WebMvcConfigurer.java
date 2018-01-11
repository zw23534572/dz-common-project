package com.dazong.example.web.configurer;


import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.dazong.common.web.JsonMappingExceptionResolver;
import com.dazong.example.web.interceptor.UserInterceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Spring MVC 配置
 * @author huqichao
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    @Bean
    public UserInterceptor userInterceptor() {
        return new UserInterceptor();
    }
    //添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor()).addPathPatterns("/**").excludePathPatterns("/**/noUser/**");
    }


    @Bean
    public JsonMappingExceptionResolver jsonMappingExceptionResolver(){
    	return new JsonMappingExceptionResolver(); 
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(
        		//时间格式转化
                SerializerFeature.WriteDateUseDateFormat, 
                //保留空的字段
                SerializerFeature.WriteMapNullValue,
                //String null -> ""
                SerializerFeature.WriteNullStringAsEmpty,
                //Number null -> 0
                SerializerFeature.WriteNullNumberAsZero);
        config.setCharset(Charset.forName("UTF-8"));
        converter.setFastJsonConfig(config);

        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        converter.setSupportedMediaTypes(supportedMediaTypes);
        converters.add(converter);
    }

}
