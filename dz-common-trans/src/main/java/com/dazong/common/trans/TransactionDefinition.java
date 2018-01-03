package com.dazong.common.trans;

import com.dazong.common.trans.annotation.Propagation;

public interface TransactionDefinition {

	String getName();

	Propagation getPropagation();
	
	long getTimeout();
	
	int getMaxTryTimes();
	
	Object[] getParams();
	
	String getBussinessId();
}
