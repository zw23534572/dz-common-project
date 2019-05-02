package com.dazong.common.trans.support;

import com.dazong.common.trans.TransactionDefinition;
import com.dazong.common.trans.annotation.Propagation;

/**
 * 事务定义
 * 
 * @author hujunzhong
 *
 */
public class DefaultTransactionDefinition implements TransactionDefinition {

	private String name;

	private Propagation propagation;

	private long timeout;

	private int maxTryTimes;
	/**
	 * 所有参数
	 */
	private Object[] params;
	/**
	 * 业务id
	 */
	private String bussinessId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Propagation getPropagation() {
		return propagation;
	}

	public void setPropagation(Propagation propagation) {
		this.propagation = propagation;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public int getMaxTryTimes() {
		return maxTryTimes;
	}

	public void setMaxTryTimes(int maxTryTimes) {
		this.maxTryTimes = maxTryTimes;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}

	public String getBussinessId() {
		return bussinessId;
	}

	public void setBussinessId(String bussinessId) {
		this.bussinessId = bussinessId;
	}

}
