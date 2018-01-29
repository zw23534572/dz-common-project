package com.dazong.common.lock.zookeeper;

import com.dazong.common.IObjectSerializer;
import com.dazong.common.lock.LockException;
import com.dazong.common.serialize.FastJsonSerializer;
import com.dazong.common.util.Assert;
import com.dazong.common.util.NumberUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.support.ApplicationObjectSupport;

/**
 * 简易Zookeeper客户端封装
 * @author Sam
 * @version 1.0.0
 */
public class ZKClient extends ApplicationObjectSupport implements InitializingBean,DisposableBean {

    private static final Logger LOG = LoggerFactory.getLogger(ZKClient.class);

    private Integer baseSleepTimeMs;

    private Integer maxRetries;

    private String server;

    private CuratorFramework curatorFramework;

    private IObjectSerializer objectSerializer;


    public void setData(String path, Object object) {
        try {
            this.curatorFramework.setData().forPath(path,objectSerializer.serialize(object));
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
            throw new LockException(e,String.format("向Zookeeper写数据时出错了，路径:%s",path));
        }
    }

    public <T> T getData(String path, Class<T> clazz) {
        try {
            byte[] bytes = this.curatorFramework.getData().forPath(path);
            return  objectSerializer.deserialize(bytes,clazz);
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
            throw new LockException(e,String.format("向Zookeeper读取数据时出错了，路径:%s",path));
        }
    }

    public void delete(String path) {
        try {
            this.curatorFramework.delete().forPath(path);
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
            throw new LockException(e,String.format("向Zookeeper删除数据时出错了，路径:%s",path));
        }
    }

    public CuratorFramework getCuratorFramework() {
        return this.curatorFramework;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            this.curatorFramework = getApplicationContext().getBean(CuratorFramework.class);
        } catch (NoSuchBeanDefinitionException e) {
            Assert.notEmpty(server);
            this.baseSleepTimeMs    = NumberUtils.defaultNumber(this.baseSleepTimeMs,20);
            this.maxRetries         = NumberUtils.defaultNumber(this.maxRetries,20);
            this.objectSerializer   = new FastJsonSerializer();
            try {
                RetryPolicy retryPolicy = new ExponentialBackoffRetry(baseSleepTimeMs, maxRetries);
                this.curatorFramework = CuratorFrameworkFactory.newClient(server, retryPolicy);
                this.curatorFramework.start();
            } catch (Exception ex) {
                LOG.error(ex.getMessage(),ex);
            }
        }
    }

    @Override
    public void destroy() throws Exception {
        try {
            if (this.curatorFramework != null) {
                this.curatorFramework.close();
            }
        } catch (Exception ex) {
            LOG.error(ex.getMessage(),ex);
        }
    }

    public void setBaseSleepTimeMs(Integer baseSleepTimeMs) {
        this.baseSleepTimeMs = baseSleepTimeMs;
    }

    public void setMaxRetries(Integer maxRetries) {
        this.maxRetries = maxRetries;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setCuratorFramework(CuratorFramework curatorFramework) {
        this.curatorFramework = curatorFramework;
    }

    public void setObjectSerializer(IObjectSerializer objectSerializer) {
        this.objectSerializer = objectSerializer;
    }
}
