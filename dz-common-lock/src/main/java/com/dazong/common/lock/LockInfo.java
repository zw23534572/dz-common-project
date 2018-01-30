package com.dazong.common.lock;

import com.dazong.common.util.DatesUtils;

/**
 * 锁信息对象
 * @author Sam
 * @version 1.0.0
 */
public interface LockInfo {

    /**
     * 锁的ID
     * @return
     */
    String getId();

    /**
     * 使用锁的模块
     * @return
     */
    String getModule();

    /**
     * 锁等待时间
     * @return
     */
    Long getWaitTime();

    /**
     * 锁超时时间
     * @return
     */
    Long getExpiredTime();

    /**
     * 锁的提供者：redis or zookeeper
     * @return
     */
    LockProviderTypeEnum getProvider();

    /**
     * 锁的URI
     * @return
     */
    String getLockURI();

    /**
     * 等待锁时间超时提示
     * @return
     */
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
