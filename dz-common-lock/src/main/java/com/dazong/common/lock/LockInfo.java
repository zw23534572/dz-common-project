package com.dazong.common.lock;

import com.dazong.common.util.DatesUtils;

/**
 * @author Sam
 * @version 1.0.0
 */
public interface LockInfo {

    String getId();

    String getModule();

    Long getWaitTime();

    Long getExpiredTime();

    LockProviderTypeEnum getProvider();

    String getLockURI();

    String getLockedAlert();

    Boolean isExpired();

    /** 默认锁的等待时间为30秒 */
    Long DEFAULT_WAIT_TIME = 30 * DatesUtils.MILLIS_PER_SECOND;

    /** 默认锁的超时时间，过了这个时间这个锁就可以被干掉 */
    Long DEFAULT_EXPIRED_TIME = 30 * DatesUtils.MILLIS_PER_MINUTE;

    /** 默认锁的提供方式为'zookeeper' */
    LockProviderTypeEnum DEFAULT_LOCK_PROVIDER = LockProviderTypeEnum.ZOOKEEPER;

    /** 默认 */
    String DEFAULT_REDIS_KEY_PREFIX = "dz:lock:";

    String DEFAULT_ZOOKEEPER_PATH_PREFIX = "/dz/lock/";

    String DEFAULT_LOCKED_ALERT = "有用户正在执行此操作，请稍候重试！";
}
