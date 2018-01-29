package com.dazong.common.lock.impl;

import com.dazong.common.lock.LockInfo;
import com.dazong.common.lock.LockProviderTypeEnum;
import com.dazong.common.lock.annotation.Locking;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Sam
 * @version 1.0.0
 */
public class SimpleLockInfo implements LockInfo {

    /** id 对应Locking.id() */
    private String id;

    private String module;

    private Long waitTime;

    private Long expiredTime;

    private LockProviderTypeEnum provider;

    private String lockedAlert;

    private Long createTime;

    public static SimpleLockInfo New(String id, String module) {
        SimpleLockInfo lock = new SimpleLockInfo();
        lock.setId(id);
        lock.setModule(module);
        lock.setExpiredTime(DEFAULT_EXPIRED_TIME);
        lock.setWaitTime(DEFAULT_WAIT_TIME);
        lock.setProvider(DEFAULT_LOCK_PROVIDER);
        lock.createTime = System.currentTimeMillis();
        return lock;
    }

    public static SimpleLockInfo of(Locking locking, String targetLockid) {
        SimpleLockInfo lock = new SimpleLockInfo();

        lock.createTime = System.currentTimeMillis();
        lock.setId(targetLockid);
        lock.setModule(locking.module());
        lock.setExpiredTime(locking.expiredTime());
        lock.setWaitTime(locking.waitTime());
        lock.setProvider(locking.provider());
        lock.setLockedAlert(locking.lockedAlert());

        return lock;
    }

    @Override
    public String getLockedAlert() {
        return lockedAlert;
    }

    @Override
    public Boolean isExpired() {
        return (System.currentTimeMillis() - createTime) > this.expiredTime ;
    }

    public void setLockedAlert(String lockedAlert) {
        this.lockedAlert = lockedAlert;
    }

    @Override
    public Long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(Long waitTime) {
        this.waitTime = waitTime;
    }

    @Override
    public Long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Long expiredTime) {
        this.expiredTime = expiredTime;
    }

    @Override
    public LockProviderTypeEnum getProvider() {
        return provider;
    }

    @Override
    public String getLockURI() {
        StringBuilder sb = new StringBuilder();
        if (provider == LockProviderTypeEnum.REDIS) {
            sb.append(DEFAULT_REDIS_KEY_PREFIX).append(module).append(":").append(id);
        } else {
            sb.append(DEFAULT_ZOOKEEPER_PATH_PREFIX).append(module).append("/").append(id);
        }
        return sb.toString();
    }

    public void setProvider(LockProviderTypeEnum provider) {
        this.provider = provider;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String toString() {
        return org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
