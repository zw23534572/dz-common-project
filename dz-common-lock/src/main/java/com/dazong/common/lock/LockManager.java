package com.dazong.common.lock;

/**
 * 分布式锁管理器，用于创建和删除锁使用。
 * @author Sam
 * @version 1.0.0
 */
public interface LockManager {

    /**
     * 创建一个锁
     * @param lockInfo 锁信息
     * @return
     */
    DistributionLock createLock(LockInfo lockInfo);

    /**
     * 创建一个新的分布式锁
     * @param module 模块
     * @param id 锁ID
     * @return
     */
    DistributionLock createLock(String module,String id);

}
