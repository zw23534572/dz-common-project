package com.dazong.common.lock;

/**
 * 锁提供者类型枚举类
 * @author Sam
 * @version 1.0.0
 */
public enum LockProviderTypeEnum {
    /** redis锁提供者类型 */
    REDIS,

    /** zookeeper锁提供者类型 */
    ZOOKEEPER
}
