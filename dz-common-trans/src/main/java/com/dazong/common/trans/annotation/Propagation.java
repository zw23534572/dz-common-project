package com.dazong.common.trans.annotation;

/**
 * 事务增强策略枚举
 * @author hjz
 *
 */
public enum Propagation {

	/**
	 * 如果当前存在事务,加入当前事务,不存在时新建事务
	 */
	REQUIRED(1),
	
	/**
	 * 如果已经存在一个事务，支持当前事务。如果没有一个活动的事务，则抛出异常。
	 */
	MANDATORY(2), 

	/**
	 * 如果当前已经存在事务,抛出异常中断.不存在时新建事务
	 */
	INTERRUPT_NOT_NEW(3),
	
	/**
	 * 如果当前存在事务,加入当前事务并创建子事务,根事务处理失败(即产生了commitFailFor异常)后,子事务不会重试.根事务成功,子事务失败,重试子事务.
	 * 当前不存在,新建事务
	 */
	NESTED(5)
	;

	private final int value;

	Propagation(int value) { this.value = value; }

	public int value() { return this.value; }
}
