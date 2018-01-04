package com.dazong.common.idempotent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wzy on 2018/1/4.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {
    /**
     * 幂等id表达式（spEL）。<br/>
     * 例如：@Idempotent("#fooReq.transId")、@Idempotent("(#fooReq.transId).concat('xx')")
     * @return
     */
    String value();
}
