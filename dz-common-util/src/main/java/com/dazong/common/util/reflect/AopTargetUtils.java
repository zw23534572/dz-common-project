package com.dazong.common.util.reflect;

import com.dazong.common.CommonStatus;
import com.dazong.common.exceptions.PlatformException;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.Field;

/**
 * 基于Spring AOP获取目标对象.
 *
 * @author huqichao
 */
public final class AopTargetUtils {

    private AopTargetUtils(){}

    /**
     * 获取目标对象.
     *
     * @param proxy 代理对象
     * @return 目标对象
     */
    public static Object getTarget(final Object proxy) {
        if (!AopUtils.isAopProxy(proxy)) {
            return proxy;
        }
        if (AopUtils.isJdkDynamicProxy(proxy)) {
            return getProxyTargetObject(proxy, "h");
        } else {
            return getProxyTargetObject(proxy, "CGLIB$CALLBACK_0");
        }
    }

    private static Object getProxyTargetObject(final Object proxy, final String proxyType) {
        Field h;
        try {
            h = proxy.getClass().getSuperclass().getDeclaredField(proxyType);
        } catch (final NoSuchFieldException ex) {
            return getProxyTargetObjectForCglibAndSpring4(proxy);
        }
        h.setAccessible(true);
        try {
            return getTargetObject(h.get(proxy));
        } catch (final IllegalAccessException ex) {
            throw new PlatformException(ex, CommonStatus.FAIL, "getProxyTargetObject");
        }
    }

    private static Object getProxyTargetObjectForCglibAndSpring4(final Object proxy) {
        Field h;
        try {
            h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
            h.setAccessible(true);
            Object dynamicAdvisedInterceptor = h.get(proxy);
            Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
            advised.setAccessible(true);
            return ((AdvisedSupport) advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();
            // CHECKSTYLE:OFF
        } catch (final Exception ex) {
            // CHECKSTYLE:ON
            throw new PlatformException(ex, CommonStatus.FAIL, "getProxyTargetObjectForCglibAndSpring4");
        }
    }

    private static Object getTargetObject(final Object object) {
        try {
            Field advised = object.getClass().getDeclaredField("advised");
            advised.setAccessible(true);
            return ((AdvisedSupport) advised.get(object)).getTargetSource().getTarget();
            // CHECKSTYLE:OFF
        } catch (final Exception ex) {
            // CHECKSTYLE:ON
            throw new PlatformException(ex, CommonStatus.FAIL, "getTargetObject");
        }
    }
}
