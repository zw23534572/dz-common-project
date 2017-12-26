package com.dazong.common.aop;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**AOP状态吗注解
 * Created by 周剑 on 2017/6/6.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ResultCode {
    int success();
    int fail();
}
