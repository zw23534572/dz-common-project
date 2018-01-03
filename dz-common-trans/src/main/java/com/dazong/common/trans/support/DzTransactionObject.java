package com.dazong.common.trans.support;

import java.io.Serializable;

/**
 * 事务对象实体,存放单个事务的数据
 * @author hujunzhong
 *
 */
public class DzTransactionObject implements Serializable{

	/**
	 * 状态:0,业务id队列,10,事务未完成;20,事务已完成(已完成的会删除掉);30,事务重试失败(考虑删除)
	 */
	public static final int STATUS_BUSSINESS_QUEUE = 0;
	public static final int STATUS_STARTED = 10;
	public static final int STATUS_END = 20;
	public static final int STATUS_FAIL = 30;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6719326147342898156L;

	/**
	 * 当前事务唯一id
	 */
	private String uid;
	
	/**
	 * 上一级父事务id
	 */
	private String pid;
	
	/**
	 * 根事务id
	 */
	private String rid;
	
	/**
	 * 业务id
	 */
	private String bussinessId;
	
	/**
	 * 状态:10,事务未完成;20,事务已完成(已完成的会删除掉);30,事务重试失败(考虑删除)
	 */
	private Integer status;
	
	/**
	 * 事务名,缺省为方法全名:包名.类名.方法名
	 */
	private String transactionName;
	
	/**
	 * 所有参数,序列化后结果
	 */
	private byte[] params;
	
	/**
	 * 下一次重试时间
	 */
	private Long retryTime;

	/**
	 * 重试次数
	 */
	private Integer retryTally;
	
	private String remark;
	
	/**
	 * 关连的fmeye traceid
	 */
	private String fmeyeId;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getBussinessId() {
		return bussinessId;
	}

	public void setBussinessId(String bussinessId) {
		this.bussinessId = bussinessId;
	}

	public String getTransactionName() {
		return transactionName;
	}

	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}

	public byte[] getParams() {
		return params;
	}

	public void setParams(byte[] params) {
		this.params = params;
	}

	public Long getRetryTime() {
		return retryTime;
	}

	public void setRetryTime(Long retryTime) {
		this.retryTime = retryTime;
	}
	
	public Integer getRetryTally() {
		return retryTally;
	}

	public void setRetryTally(Integer retryTally) {
		this.retryTally = retryTally;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFmeyeId() {
		return fmeyeId;
	}

	public void setFmeyeId(String fmeyeId) {
		this.fmeyeId = fmeyeId;
	}
	
}
