package com.dazong.common.trans.listener;

import java.io.Serializable;
import java.lang.reflect.Method;

import lombok.Data;

@Data
public class RetryFailEvent implements Serializable{
	
	private static final long serialVersionUID = 8216662080744121904L;

	private String transactionId;
	
	private String transactionName;
	
	private Method Method;
	
	private Object[] params;
	
	private Object obj;
}
