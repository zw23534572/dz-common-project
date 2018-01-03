package com.dazong.common.trans.aspect;

import com.dazong.common.trans.TransactionDefinition;

public interface TransactionAttribute extends TransactionDefinition {

	boolean commitFailOn(Throwable ex);

	boolean retryOn(Throwable ex);

	boolean isAsync();
}
