package com.dazong.common.trans;

public class DzTransactionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3906827017056676612L;

	public DzTransactionException(){
		super();
	}
	
	public DzTransactionException(String msg){
		super(msg);
	}

	
	public DzTransactionException(String msg, Throwable t){
		super(msg, t);
	}
}
