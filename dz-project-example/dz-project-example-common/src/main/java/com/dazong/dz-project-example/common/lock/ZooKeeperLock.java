package com.dazong.dz-project-example.common.lock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * ZooKeeperLock 锁
 * @author huqichao
 */
public class ZooKeeperLock {

    private Logger logger = LoggerFactory.getLogger(ZooKeeperLock.class);

    private static final int DEFAULT_LOCK_TIME = 10;

    private static final String ROOT = "/locks/";

    private CuratorFramework cf;

    private InterProcessMutex mutex;
    private String lockName;

    public ZooKeeperLock(CuratorFramework cf){
        this.cf = cf;
    }

    public void lock(String lockName) {
        lock(lockName, DEFAULT_LOCK_TIME, TimeUnit.MINUTES);
    }

    public void lock(String lockName, long time, TimeUnit unit) {
        this.lockName = lockName;
        this.mutex = new InterProcessMutex(cf, ROOT + lockName);
        boolean locked;
        try {
            locked = this.mutex.acquire(time, unit);
        } catch (Exception e){
            throw new LockException("获取锁失败:" + lockName);
        }
        if (!locked){
            throw new LockException("获取锁超时:" + lockName);
        }
    }

    public void unlock(){
        try {
            if(this.mutex.isAcquiredInThisProcess()){
                this.mutex.release();
            }
        } catch (Exception e) {
            logger.error("释放锁失败:"+lockName, e);
        }
    }

    private class LockException extends RuntimeException {

        private static final long serialVersionUID = -4079119306525513596L;

        public LockException(String message){
            super(message);
        }
    }
}
