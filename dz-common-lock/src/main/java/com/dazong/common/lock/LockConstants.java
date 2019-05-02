package com.dazong.common.lock;

import com.dazong.common.util.DatesUtils;

/**
 * Lock常量定义
 * @author Sam
 * @version 1.0.0
 */
public final class LockConstants {

    /** 默认锁的等待时间为30秒 */
    public static final Long DEFAULT_WAIT_TIME = 30 * DatesUtils.MILLIS_PER_SECOND;

    /** 默认锁的超时时间，过了这个时间这个锁就可以被干掉 */
    public static final Long DEFAULT_EXPIRED_TIME = 30 * DatesUtils.MILLIS_PER_MINUTE;

    /** 默认锁的提供方式为'zookeeper' */
    public static final LockProviderTypeEnum DEFAULT_LOCK_PROVIDER = LockProviderTypeEnum.ZOOKEEPER;

    /** Redis默认的key前缀 */
    public static final String DEFAULT_REDIS_KEY_PREFIX = "dz:lock:";

    /** Zookeeper默认的路径前缀 */
    public static final String DEFAULT_ZOOKEEPER_PATH_PREFIX = new StringBuilder("/dz/lock/").toString();

    /** 默认的错误提示 */
    public static final String DEFAULT_LOCKED_ALERT = "有用户正在执行此操作，请稍候重试！";

    private LockConstants(){}
}
