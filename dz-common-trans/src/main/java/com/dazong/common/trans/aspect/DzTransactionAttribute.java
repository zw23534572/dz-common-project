package com.dazong.common.trans.aspect;

import com.dazong.common.trans.support.DefaultTransactionDefinition;

@SuppressWarnings("serial")
public class DzTransactionAttribute extends DefaultTransactionDefinition implements TransactionAttribute {
	
	private Class<? extends Throwable>[] commitFailOn;
	private Class<? extends Throwable>[] retryOn;
	private boolean async;

	public boolean commitFailOn(Throwable ex) {
		return matchException(commitFailOn, ex);
	}

	public boolean retryOn(Throwable ex) {
		if(retryOn == null || retryOn.length == 0){
			return ex instanceof RuntimeException || ex instanceof Error;
		}
		
		return matchException(retryOn, ex);
	}
	
	private boolean matchException(Class<? extends Throwable>[] contains, Throwable ex){
		if(contains == null){
			return false;
		}
		
		Class<? extends Throwable> exClass = ex.getClass();
		for (Class<? extends Throwable> throwable : contains) {
			if(exClass.equals(throwable) || exClass.isAssignableFrom(throwable) ){
				return true;
			}
		}
		
		return false;
	}

	public Class<? extends Throwable>[] getCommitFailOn() {
		return commitFailOn;
	}

	public void setCommitFailOn(Class<? extends Throwable>[] commitFailOn) {
		this.commitFailOn = commitFailOn;
	}

	public Class<? extends Throwable>[] getRetryOn() {
		return retryOn;
	}

	public void setRetryOn(Class<? extends Throwable>[] retryOn) {
		this.retryOn = retryOn;
	}

	public boolean isAsync() {
		return async;
	}

	public void setAsync(boolean async) {
		this.async = async;
	}

}
