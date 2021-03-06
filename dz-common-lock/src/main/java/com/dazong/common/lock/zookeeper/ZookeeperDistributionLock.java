package com.dazong.common.lock.zookeeper;

import com.dazong.common.lock.BaseDistributionLock;
import com.dazong.common.lock.DistributionLock;
import com.dazong.common.lock.LockException;
import com.dazong.common.lock.LockInfo;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * 基于ZK的InterProcessMutex实现的分布式锁
 * @author Sam
 * @version 1.0.0
 */
public class ZookeeperDistributionLock extends BaseDistributionLock implements DistributionLock {

    private static final Logger LOG = LoggerFactory.getLogger(ZookeeperDistributionLock.class);

    private InterProcessMutex innerLock;

    private LockInfo lockInfo;

    private TimeUnit timeUnit;

    private ZookeeperClient zkclient;


    public ZookeeperDistributionLock(ZookeeperClient zkclient, LockInfo lockInfo) {
        this.zkclient   = zkclient;
        this.innerLock  = new InterProcessMutex(zkclient.getCuratorFramework(),lockInfo.getLockURI());
        this.timeUnit   = TimeUnit.MILLISECONDS;
        this.lockInfo   = lockInfo;
    }


    @Override
    public void lock() {
        try {
            //计时器
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            //logging
            LOG.info(" Try to get lock-> lockId:{},lockURI:{},lockProvider:{},waitTime:{},expiredTime:{},startMills:{}",
                            lockInfo.getId(),
                            lockInfo.getLockURI(),
                            lockInfo.getProvider(),
                            lockInfo.getWaitTime(),
                            lockInfo.getExpiredTime(),
                            stopWatch.getStartTime());

            if (innerLock.isAcquiredInThisProcess()) {
                return;
            }

            if (innerLock.acquire(lockInfo.getWaitTime(), timeUnit)) {
                //设置当前时间到这个锁的路径中的value，用于计算超时
                zkclient.setData(lockInfo.getLockURI(), System.currentTimeMillis());
                LOG.info(" Get a lock~" );
                return;
            }

            //持有锁的线程是否时间超时了，如果超时了，直接干掉
            Long lockCreateTime = zkclient.getData(lockInfo.getLockURI(),Long.class);
            if (lockCreateTime != null) {
                long currTime   = System.currentTimeMillis();
                //超时了直接干掉，让要获取锁的线程再取锁
                if ((currTime - lockCreateTime) > lockInfo.getExpiredTime()) {
                    zkclient.delete(lockInfo.getLockURI());
                    lock();//retry
                } else {
                    stopWatch.stop();
                    LOG.warn(" Wait timeout[{}]! ",stopWatch.getTime());
                    throw new LockException(lockInfo.getLockedAlert());
                }
            }
        } catch (LockException ex) {
                throw   ex;
        } catch (Exception ex) {
            LOG.error(ex.getMessage(),ex);
            throw new LockException(ex,String.format("获取锁%s失败了!",lockInfo.getLockURI()));
        }
    }


    @Override
    public boolean tryLock() {
        return  tryLock(lockInfo.getWaitTime(),timeUnit);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit)  {
        try {
            return innerLock.acquire(time, unit);
        } catch (Exception ex) {
            LOG.error(ex.getMessage(),ex);
            throw new LockException(ex,String.format("获取锁%s失败了！",lockInfo.getLockURI()));
        }
    }

    @Override
    public void unlock() {
        if (innerLock != null && innerLock.isAcquiredInThisProcess()) {
            try {
                innerLock.release();
            } catch (Exception ex) {
                throw new LockException(ex,String.format("释放锁%s失败了！",lockInfo.getLockURI()));
            }
        }
    }

}
