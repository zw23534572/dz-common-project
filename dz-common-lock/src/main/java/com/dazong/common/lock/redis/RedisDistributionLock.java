package com.dazong.common.lock.redis;

import com.dazong.common.lock.BaseDistributionLock;
import com.dazong.common.lock.DistributionLock;
import com.dazong.common.lock.LockException;
import com.dazong.common.lock.LockInfo;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * redis分布式业务锁，基于Redis的这个命令实现：SET key value [EX seconds] [PX milliseconds] [NX|XX]
 * 详见：http://redisdoc.com/string/set.html
 * @author Sam
 * @version 1.0.0
 */
public class RedisDistributionLock extends BaseDistributionLock implements DistributionLock {

    private static final Logger LOG = LoggerFactory.getLogger(RedisDistributionLock.class);

    //redis
    private RedisTemplate redisTemplate;

    //锁信息
    private LockInfo lockInfo;

    //重入次数
    private AtomicLong lockCount = new AtomicLong(0);

    //本地VM的锁缓存
    private static Map<Thread,LockInfo> threadData = new ConcurrentHashMap<>();

    //锁状态
    private static Map<String,Object> lockStatusData = new ConcurrentHashMap<>();

    public RedisDistributionLock(RedisTemplate redisTemplate,LockInfo lockInfo) {
        this.redisTemplate = redisTemplate;
        this.lockInfo = lockInfo;
    }

    @Override
    public void lock() {
        if (!tryLock()) {
            throw new LockException(lockInfo.getLockedAlert());
        }
    }


    @Override
    public boolean tryLock() {
        try {
            return tryLock(lockInfo.getWaitTime(),TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new LockException(e,"获取锁失败了！");
        }
    }

    @Override
    public boolean tryLock(long waitTime, TimeUnit unit) throws InterruptedException {

        //当前线程是否已经获得锁
        StopWatch stopWatch     = new StopWatch();
        Thread currentThread    = Thread.currentThread();
        LockInfo existsLockInfo = threadData.get(currentThread);
        stopWatch.start();

        //logging
        LOG.info(" Try to get lock-> lockId:{},lockURI:{},lockProvider:{},waitTime:{},expiredTime:{},startMills:{}",
                this.lockInfo.getId(),
                this.lockInfo.getLockURI(),
                this.lockInfo.getProvider(),
                this.lockInfo.getWaitTime(),
                this.lockInfo.getExpiredTime(),
                stopWatch.getStartTime());

        //重入加1
        if (existsLockInfo != null) {
            lockCount.incrementAndGet();
            return true;
        }

        //在当前VM下的其他线程是否已经获得锁
        if (lockStatusData.get(this.lockInfo.getLockURI()) != null)
            return false;

        //获取实际的锁
        long timeout = unit.toMillis(waitTime);
        Object lock  = this.lockInfo.getLockURI().intern();
        synchronized (lock) {
            while ( timeout >= 0 && existsLockInfo == null) {
                timeout -= 100;
                if (timeout >= 100) {
                    lock.wait(100);
                }
                if (attemptRedisLock()) {
                    lockCount.incrementAndGet();
                    threadData.put(currentThread, this.lockInfo);
                    lockStatusData.put(this.lockInfo.getLockURI(),new Object());
                    LOG.info("Get a Lock ^-^ ");
                    return true;
                }
            }
        }
        //在waitTime时间内获取不到锁
        stopWatch.stop();
        LOG.warn("wait timeout[{}]",stopWatch.getTime());
        return false;
    }

    @Override
    public void unlock() {
        //当前线程是否已经获得锁
        Thread currentThread    = Thread.currentThread();
        LockInfo lockInfo       = threadData.get(currentThread);
        if (lockInfo == null || lockCount.decrementAndGet() > 0)
            return;
        threadData.remove(currentThread);
        lockStatusData.remove(lockInfo.getLockURI());
        lockCount.set(0);
        releaseRedisLock();

    }

    /** 从redis获取分布式锁 */
    private Boolean attemptRedisLock() {

        return (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                Jedis jedis = (Jedis) redisConnection.getNativeConnection();
                return SUCCESS.equals(jedis.set(lockInfo.getLockURI(),lockInfo.getId(),"NX","PX",lockInfo.getExpiredTime()));
            }
        });
    }

    /** 释放redis锁 */
    private Boolean releaseRedisLock() {
        return (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    connection.del(lockInfo.getLockURI().getBytes());
                    return Boolean.TRUE;
                } catch (Exception e) {
                    LOG.error(e.getMessage(),e);
                    throw new LockException(e,"释放锁出错了！");
                }
            }
        });
    }

    private static final String SUCCESS = "OK";


}
