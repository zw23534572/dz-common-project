package com.dazong.common.lock;

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

    /**
     * 是否已经超时
     * @return
     */
    Boolean isExpired();

}
