package com.dazong.common.lock.impl;

import com.dazong.common.lock.DistributionLock;
import com.dazong.common.lock.LockInfo;
import com.dazong.common.lock.LockManager;
import com.dazong.common.lock.LockProviderTypeEnum;
import com.dazong.common.lock.redis.RedisDistributionLock;
import com.dazong.common.lock.zookeeper.ZookeeperClient;
import com.dazong.common.lock.zookeeper.ZookeeperDistributionLock;
import com.dazong.common.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * LockManager实现类
 * @author Sam
 * @version 1.0.0
 */
public class LockManagerImpl extends ApplicationObjectSupport implements LockManager,ApplicationContextAware,InitializingBean {

    /** LOGGER */
    private static final Logger LOG = LoggerFactory.getLogger(LockManagerImpl.class);

    /**用于创建redis锁，以及管理锁的相关信息*/
    private RedisTemplate redisTemplate;

    /**基于zookeeper锁节点的工具类*/
    private ZookeeperClient zookeeperClient;


    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setZookeeperClient(ZookeeperClient zookeeperClient) {
        this.zookeeperClient = zookeeperClient;
    }

    @Override
    public DistributionLock createLock(LockInfo lockInfo) {

        Assert.notNull(lockInfo);

        LOG.debug("即将创建一个新的锁->ID:{},URI:{}",lockInfo.getId(),lockInfo.getLockURI());

        if (lockInfo.getProvider() == LockProviderTypeEnum.ZOOKEEPER) {
            return new ZookeeperDistributionLock(this.zookeeperClient, lockInfo);
        }

        return new RedisDistributionLock(redisTemplate,lockInfo);
    }

    @Override
    public DistributionLock createLock(String module, String id) {
        return createLock(SimpleLockInfo.of(id,module));
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.zookeeperClient,"ZKClient为空！");
    }

}
