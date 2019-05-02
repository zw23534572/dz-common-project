package com.dazong.common.trans.test.exception;

public class TransExcetion extends RuntimeException {

	private static final long serialVersionUID = 4741580352996864240L;
	
	public TransExcetion(){
		
	}
	
	public TransExcetion(String msg){
		super(msg);
	}
}
