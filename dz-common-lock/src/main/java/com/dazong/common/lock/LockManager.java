package com.dazong.common.lock;

/**
 * 分布式锁管理器，用于创建和删除锁使用。
 * @author Sam
 * @version 1.0.0
 */
public interface LockManager {

    /**
     * 创建一个锁
     * @param lockInfo
     * @return
     */
    DistributionLock createLock(LockInfo lockInfo);

    DistributionLock createLock(String module,String id);

    void removeLock(LockInfo lockInfo);
}
