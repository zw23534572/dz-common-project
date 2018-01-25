package com.dazong.common.lock;

import java.io.Closeable;
import java.util.concurrent.locks.Lock;

/**
 * 分布式锁，继承自jdk lock接口即可
 * @author Sam
 * @version 1.0.0
 */
public interface DistributionLock extends Lock ,Closeable{
}
