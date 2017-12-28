package com.dazong.common.web;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.FactoryBean;

import com.dazong.common.util.DateFormatUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author luobinwen
 *
 */
public class JsonObjectMapperFactoryBean implements FactoryBean<ObjectMapper> {

    @Override
    public ObjectMapper getObject() throws Exception {
        ObjectMapper objMapper = new ObjectMapper();
        objMapper.setDateFormat(new SimpleDateFormat(DateFormatUtils.DATE_TIME_FORMAT));
        objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objMapper;
    }

    @Override
    public Class<?> getObjectType() {
        return ObjectMapper.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

}
