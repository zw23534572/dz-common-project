package com.dazong.common.trans.listener;

import java.lang.reflect.Method;

import lombok.Data;

@Data
public class RetryFailEvent{
	
	private String transactionId;
	
	private String transactionName;
	
	private Method Method;
	
	private Object[] params;
	
	private Object obj;
}
