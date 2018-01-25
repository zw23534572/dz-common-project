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

}
