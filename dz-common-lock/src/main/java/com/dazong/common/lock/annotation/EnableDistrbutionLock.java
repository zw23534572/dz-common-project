package com.dazong.common.lock.annotation;

import java.lang.annotation.*;

/**
 * 基于sprinboot自动配置的开关
 * @author Sam
 * @version 1.0.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EnableDistrbutionLock {
}
