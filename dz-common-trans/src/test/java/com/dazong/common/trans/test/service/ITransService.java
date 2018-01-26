package com.dazong.common.trans.test.service;

public interface ITransService {
	/**
	 * <B>方法名称：</B><BR>
	 * <B>概要说明：不嵌套事务，不抛异常</B><BR>
	 * @param name
	 */
	void doTrans(String name);
	/**
	 * <B>方法名称：</B><BR>
	 * <B>概要说明：不嵌套事务，抛出异常</B><BR>
	 * @param name
	 */
	void doTransException(String name);
	
	/**
	 * <B>方法名称：</B><BR>
	 * <B>概要说明：不嵌套事务，抛出异常,但是指定异常提交事务</B><BR>
	 * @param name
	 */
	void doTransExceptionButRollback(String name);
	
	/**
	 * <B>方法名称：测试事务的传播特性</B><BR>
	 * <B>概要说明：MANDATORY：如果已经存在一个事务，支持当前事务。如果没有一个活动的事务，则抛出异常。
	 * </B><BR>
	 * @param name
	 */
	void doTransPropagationForMandatory1(String name);
	
	void doTransPropagationForMandatory2(String name);

}
