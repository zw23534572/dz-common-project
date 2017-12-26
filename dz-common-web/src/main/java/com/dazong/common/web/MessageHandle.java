package com.dazong.common.web;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;

/**
 * @名称: MessageHandle
 * @描述: 信息资源负责类
 *
 */
public class MessageHandle  implements MessageSourceAware{
	protected MessageSourceAccessor messageSourceAccessor;
	
	public MessageHandle(){
	}
	public MessageHandle(MessageSource messageSource){
		messageSourceAccessor=new MessageSourceAccessor(messageSource);
	}
	
	public void setMessageSource(MessageSource messageSource) {
		messageSourceAccessor = new MessageSourceAccessor(messageSource);
	}
	
	public String getMessage(String code){
		return messageSourceAccessor.getMessage(code);
	}
	
	public String getMessage(String code,String defaultMessage){
		return messageSourceAccessor.getMessage(code,defaultMessage);
	}
	
	public String getMessage(String code,Object... objs){
		return messageSourceAccessor.getMessage(code,objs);
	}
	public String getMessage(String code,String defaultMessage,Object... objs){
		return messageSourceAccessor.getMessage(code,objs,defaultMessage);
	}

}


