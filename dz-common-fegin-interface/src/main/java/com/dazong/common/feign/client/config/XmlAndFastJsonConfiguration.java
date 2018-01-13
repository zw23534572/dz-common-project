package com.dazong.common.feign.client.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.dazong.common.feign.client.utils.ConverterUtils;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.support.ResponseEntityDecoder;
import org.springframework.cloud.netflix.feign.support.SpringDecoder;
import org.springframework.cloud.netflix.feign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.xml.XmlAwareFormHttpMessageConverter;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lori.li
 */
@Configuration
public class XmlAndFastJsonConfiguration {

    private ObjectFactory<HttpMessageConverters> messageConverters;

    public XmlAndFastJsonConfiguration() {
        FastJsonHttpMessageConverter converter = ConverterUtils.createFastJsonHttpMessageConverter(MediaType.TEXT_HTML,MediaType.APPLICATION_JSON_UTF8);
        XmlAwareFormHttpMessageConverter converter1 = new XmlAwareFormHttpMessageConverter();
        final HttpMessageConverters converters = new HttpMessageConverters(converter, converter1);
        messageConverters = new ObjectFactory<HttpMessageConverters>() {
            @Override
            public HttpMessageConverters getObject() throws BeansException {
                return converters;
            }
        };

    }

    @Bean
    @ConditionalOnMissingBean
    public Decoder feignDecoder() {
        return new ResponseEntityDecoder(new SpringDecoder(messageConverters));
    }

    @Bean
    @ConditionalOnMissingBean
    public Encoder feignEncoder() {
        return new SpringEncoder(this.messageConverters);
    }
}
