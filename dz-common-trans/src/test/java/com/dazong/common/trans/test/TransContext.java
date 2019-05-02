package com.dazong.common.trans.test;

import java.util.concurrent.ConcurrentHashMap;

public class TransContext extends ConcurrentHashMap<String, Object>{
	
	private static final long serialVersionUID = -3863843823815191182L;

	protected static Class<? extends TransContext> contextClass = TransContext.class;
	
	protected static final ThreadLocal<? extends TransContext> threadLocal = new ThreadLocal<TransContext>() {
        @Override
        protected TransContext initialValue() {
            try {
                return contextClass.newInstance();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    };
    
    public static TransContext getCurrentContext() {
    	TransContext context = threadLocal.get();
        return context;
    }
    
    public void set(String key, Object value) {
        if (value != null) put(key, value);
        else remove(key);
    }
    
    public void unset() {
        threadLocal.remove();
    }
}
