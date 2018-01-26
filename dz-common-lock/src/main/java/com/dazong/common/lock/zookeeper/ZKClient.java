package com.dazong.common.lock.zookeeper;

import com.dazong.common.lock.LockException;
import com.dazong.common.util.Assert;
import com.dazong.common.util.NumberUtils;
import com.dazong.common.util.serialize.FastjsonObjectSerializer;
import com.dazong.common.util.serialize.ObjectSerializer;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 简易Zookeeper客户端封装
 * @author Sam
 * @version 1.0.0
 */
public class ZKClient implements InitializingBean,DisposableBean {

    private static Logger logger = LoggerFactory.getLogger(ZKClient.class);

    private Integer baseSleepTimeMs;

    private Integer maxRetries;

    private String server;

    private CuratorFramework curatorFramework;

    private ObjectSerializer objectSerializer;


    public void setData(String path, Object object) {
        try {
            this.curatorFramework.setData().forPath(path,objectSerializer.serialize(object));
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new LockException(e,String.format("向Zookeeper写数据时出错了，路径:%s",path));
        }
    }

    public <T> T getData(String path, Class<T> clazz) {
        try {
            byte[] bytes = this.curatorFramework.getData().forPath(path);
            return  objectSerializer.deserialize(bytes,clazz);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new LockException(e,String.format("向Zookeeper读取数据时出错了，路径:%s",path));
        }
    }

    public void delete(String path) {
        try {
            this.curatorFramework.delete().forPath(path);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new LockException(e,String.format("向Zookeeper删除数据时出错了，路径:%s",path));
        }
    }

    public CuratorFramework getCuratorFramework() {
        return this.curatorFramework;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notEmpty(server);
        this.baseSleepTimeMs    = NumberUtils.defaultNumber(this.baseSleepTimeMs,20);
        this.maxRetries         = NumberUtils.defaultNumber(this.maxRetries,20);
        this.objectSerializer   = new FastjsonObjectSerializer();
        try {
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(baseSleepTimeMs, maxRetries);
            this.curatorFramework = CuratorFrameworkFactory.newClient(server, retryPolicy);
            this.curatorFramework.start();
        } catch (Exception ex) {
            logger.error(ex.getMessage(),ex);
        }
    }

    @Override
    public void destroy() throws Exception {
        try {
            if (this.curatorFramework != null) {
                this.curatorFramework.close();
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(),ex);
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

    public void setObjectSerializer(ObjectSerializer objectSerializer) {
        this.objectSerializer = objectSerializer;
    }
}
