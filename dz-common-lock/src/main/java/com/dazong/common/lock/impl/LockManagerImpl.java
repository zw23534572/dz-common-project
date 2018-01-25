package com.dazong.common.lock.impl;

import com.dazong.common.lock.DistributionLock;
import com.dazong.common.lock.LockInfo;
import com.dazong.common.lock.LockManager;
import com.dazong.common.lock.LockProviderTypeEnum;
import com.dazong.common.lock.redis.RedisDistributionLock;
import com.dazong.common.lock.util.ZKClient;
import com.dazong.common.lock.zookeeper.ZookeeperDistributionLock;
import com.dazong.common.util.Assert;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * LockManager实现类
 * @author Sam
 * @version 1.0.0
 */
public class LockManagerImpl implements LockManager {


    private RedisTemplate redisTemplate;

    private ZKClient zkClient;


    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setZkClient(ZKClient zkClient) {
        this.zkClient = zkClient;
    }

    @Override
    public DistributionLock createLock(LockInfo lockInfo) {

        Assert.notNull(lockInfo);
        if (lockInfo.getProvider() == LockProviderTypeEnum.ZOOKEEPER)
            return new ZookeeperDistributionLock(this.zkClient,lockInfo);
        return new RedisDistributionLock(redisTemplate,lockInfo);
    }

    @Override
    public DistributionLock createLock(String module, String id) {

        return createLock(SimpleLockInfo.New(id,module));
    }

    @Override
    public void removeLock(LockInfo lockInfo) {
        throw new UnsupportedOperationException();
    }


}
