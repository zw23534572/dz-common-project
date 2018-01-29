package com.dazong.common.lock.impl;

import com.dazong.common.lock.DistributionLock;
import com.dazong.common.lock.LockInfo;
import com.dazong.common.lock.LockManager;
import com.dazong.common.lock.LockProviderTypeEnum;
import com.dazong.common.lock.redis.RedisDistributionLock;
import com.dazong.common.lock.zookeeper.ZKClient;
import com.dazong.common.lock.zookeeper.ZookeeperDistributionLock;
import com.dazong.common.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * LockManager实现类
 * @author Sam
 * @version 1.0.0
 */
public class LockManagerImpl extends ApplicationObjectSupport implements LockManager,ApplicationContextAware,InitializingBean {


    //LOG
    private static Logger LOG = LoggerFactory.getLogger(LockManagerImpl.class);

    /**用于创建redis锁，以及管理锁的相关信息*/
    @Autowired
    private RedisTemplate redisTemplate;

    /**基于zookeeper锁节点的工具类*/
    @Autowired
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

        LOG.debug(lockInfo.toString());

        if (lockInfo.getProvider() == LockProviderTypeEnum.ZOOKEEPER)
            return new ZookeeperDistributionLock(this.zkClient,lockInfo);
        return new RedisDistributionLock(redisTemplate,lockInfo);
    }

    @Override
    public DistributionLock createLock(String module, String id) {

        return createLock(SimpleLockInfo.New(id,module));
    }


    @Override
    public void afterPropertiesSet() throws Exception {
    }

}
