package com.dazong.common.feign.client.utils;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.http.MediaType;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lori.li
 */
public class ConverterUtils {

    public static FastJsonHttpMessageConverter createFastJsonHttpMessageConverter(MediaType testHtml, MediaType applicationJsonUtf8) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullNumberAsZero);
        config.setCharset(Charset.forName("UTF-8"));
        converter.setFastJsonConfig(config);

        List<MediaType> supportedMediaTypes = new ArrayList<>();
        if (null != testHtml)
            supportedMediaTypes.add(MediaType.TEXT_HTML);
        if (null != applicationJsonUtf8)
            supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        converter.setSupportedMediaTypes(supportedMediaTypes);
        return converter;
    }
}
