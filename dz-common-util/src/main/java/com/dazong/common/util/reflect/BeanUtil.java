package com.dazong.common.util.reflect;

import com.dazong.common.CommonStatus;
import com.dazong.common.exceptions.PlatformException;
import com.dazong.common.util.Assert;
import com.dazong.common.util.CommonUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 基于BeanUtils而重构的代码
 *
 * @author Sam
 * @version 1.0.0
 * @since 1.0.0
 */
public class BeanUtil extends BeanUtils {

    /**
     * bean copy
     *
     * @param target        目标Bean
     * @param source        来源Bean
     * @param withOutFields 不需要COPY值的字段
     */
    public static void copyPropertiesWithout(Object target, Object source, String... withOutFields) {
        try {
            BeanUtils.copyProperties(target, source);
            if (withOutFields != null) {
                for (String f : withOutFields) {
                    PropertyUtils.setProperty(target, f, null);
                }
            }
        } catch (Exception e) {
            throw new PlatformException(e, CommonStatus.FAIL, " bean copy");
        }
    }

    /**
     * copy一个bean
     *
     * @param sourceObject      源对象
     * @param targetObjectClass 目标对象类型
     * @param withOutFields     不需要copy的属性集
     * @return
     */
    public static <T> T copy(Object sourceObject, Class<T> targetObjectClass, String... withOutFields) {
        if (sourceObject == null) {
            return null;
        }
        T targetObject = ClassWrapper.wrap(targetObjectClass).newOne();
        copyProperties(targetObject, sourceObject);
        try {
            if (withOutFields != null) {
                for (String f : withOutFields) {
                    PropertyUtils.setProperty(targetObject, f, null);
                }
            }
        } catch (Exception e) {
            throw new PlatformException(e, CommonStatus.FAIL, " copy一个bean");
        }
        return targetObject;
    }

    /**
     * copy一个bean集合
     *
     * @param sourceObjects     源集合
     * @param targetObjectClass 目标bean的Class
     * @param withOutFields     不需要copy的属性
     * @param <T>               目标class泛型
     * @return
     */
    public static <T> List<T> copyObjects(List sourceObjects, Class<T> targetObjectClass, String... withOutFields) {
        if (CommonUtil.isEmpty(sourceObjects)) {
            return new ArrayList<>();
        }
        List<T> targetObjects = CommonUtil.arrayList(sourceObjects.size());
        for (Iterator iter = sourceObjects.iterator(); iter.hasNext(); ) {
            T targetObject = copy(iter.next(), targetObjectClass, withOutFields);
            if (targetObject != null) {
                targetObjects.add(targetObject);
            }
        }
        return targetObjects;
    }

    /**
     * bean copy
     *
     * @param target 目标Bean
     * @param source 来源Bean
     */
    public static void copyProperties(Object target, Object source) {
        org.springframework.beans.BeanUtils.copyProperties(source, target);
    }

    /**
     * 获取bean属性
     *
     * @param bean
     * @param name
     * @return
     */
    public static String getSimpleProperty(Object bean, String name) {
        Assert.isEmpty(bean, name);
        try {
            return org.apache.commons.beanutils.BeanUtils.getSimpleProperty(bean, name);
        } catch (Exception e) {
            throw new PlatformException(e, CommonStatus.FAIL, " bean copy");
        }
    }

    /**
     * 设置Bean属性
     *
     * @param bean
     * @param name
     * @param value
     */
    public static void setProperty(Object bean, String name, Object value) {
        try {
            org.apache.commons.beanutils.BeanUtils.setProperty(bean, name, value);
        } catch (Exception e) {
            throw new PlatformException(e, CommonStatus.FAIL, " 设置Bean属性");
        }
    }

    public static void populate(Object bean, Map properties) {
        try {
            org.apache.commons.beanutils.BeanUtils.populate(bean, properties);
        } catch (Exception e) {
            throw new PlatformException(e, CommonStatus.FAIL, " populate");
        }
    }

    public static void populateValue(Object bean, Map properties) {
        try {
            org.apache.commons.beanutils.BeanUtils.populate(bean, properties);
        } catch (Exception e) {
            throw new PlatformException(e, CommonStatus.FAIL, " populateValue");
        }
    }

}
