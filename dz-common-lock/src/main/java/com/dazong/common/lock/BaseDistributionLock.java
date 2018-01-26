package com.dazong.common.lock;

import java.io.IOException;
import java.util.concurrent.locks.Condition;

/**
 * @author Sam
 * @version 1.0.0
 */
public abstract class BaseDistributionLock implements DistributionLock,Cloneable {

    @Override
    public void lockInterruptibly() throws InterruptedException {
        throw  new UnsupportedOperationException();
    }

    @Override
    public Condition newCondition() {
        throw   new UnsupportedOperationException();
    }

    @Override
    public void close() throws IOException {
        unlock();
    }
}
