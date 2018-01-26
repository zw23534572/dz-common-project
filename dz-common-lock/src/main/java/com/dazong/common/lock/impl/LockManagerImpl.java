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
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * LockManager实现类
 * @author Sam
 * @version 1.0.0
 */
public class LockManagerImpl implements LockManager,ApplicationContextAware,InitializingBean {


    //logger
    private static Logger logger = LoggerFactory.getLogger(LockManagerImpl.class);

    /**用于创建redis锁，以及管理锁的相关信息*/
    private RedisTemplate redisTemplate;

    /**基于zookeeper锁节点的工具类*/
    private ZKClient zkClient;

    /** spring 上下文 */
    private ApplicationContext applicationContext;

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setZkClient(ZKClient zkClient) {
        this.zkClient = zkClient;
    }

    @Override
    public DistributionLock createLock(LockInfo lockInfo) {

        Assert.notNull(lockInfo);

        logger.debug(lockInfo.toString());

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
        Assert.notNull(applicationContext);

        this.redisTemplate = applicationContext.getBean(RedisTemplate.class);
        this.zkClient      = applicationContext.getBean(ZKClient.class);

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
