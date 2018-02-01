package com.dazong.common.lock.annotation;

import com.dazong.common.lock.LockConstants;
import com.dazong.common.lock.LockProviderTypeEnum;

import java.lang.annotation.*;

/**
 * 分布式锁AOP拦截配置
 *
 * @author Sam
 * @version 1.0.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Locking {

    /** 锁的ID定义，支持spel */
    String id() default "";

    /**
     * 要用锁的模块
     * 1.为zookeeper的话，会把此属性加到全局路径/dz/lock下，如module为'WR',value为'20173243'，
     *   那么在zk的锁路径则为:/dz/lock/WR/20173243.
     * 2.为redis的话，会把此属性加到redis的key前缀中，如module为'WR'，value为'20173243',
     *   那么redis的锁的key为： dz:lock:wr:20172343
     * @return
     */
    String module() ;

    /**
     * 获取锁的等待时间，默认等待时间为30秒，时间单位为微秒
     * @return
     */
    long waitTime() default 30 * 1000;

    /**
     * 锁的失效时间，此时间必须大于waitTime，默认过期时间为30分，过了这个时间这个锁就无效了，时间单位为微秒
     * @return
     */
    long expiredTime() default 30 * 60 * 1000;

    /**
     * 条件，支持SPEL，条件成功才会加锁
     * @return
     */
    String condition() default "";

    /**
     * 锁的提供方式，可以是redis，也可以是zookeeper，根据实际的场景来做选择
     * @return
     */
    LockProviderTypeEnum provider() default LockProviderTypeEnum.ZOOKEEPER;

    /**
     * 如果被锁了(即获取锁的时间超过了waitTime)要提示什么信息给用户，默认为“有用户正执行此操作，请稍候重试！”
     * @return
     */
    String lockedAlert() default LockConstants.DEFAULT_LOCKED_ALERT;

}
