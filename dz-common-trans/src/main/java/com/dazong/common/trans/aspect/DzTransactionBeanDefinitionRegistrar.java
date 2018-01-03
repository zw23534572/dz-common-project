package com.dazong.common.trans.aspect;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import com.dazong.common.trans.support.DzTransactionSupport;
import com.dazong.common.trans.support.DzTransactionTask;

/**
 * 大宗最终一致性事务bean注册器
 * @author hujunzhong
 *
 */
public class DzTransactionBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		//使用dztask
		if(DzTransactionSupport.usedClass("com.dazong.scheduler.lts.annotation.DzTask")){
			if(!registry.containsBeanDefinition(DzTransactionTask.class.getName())){
				BeanDefinitionBuilder autoConfigDef = BeanDefinitionBuilder.rootBeanDefinition(DzTransactionTask.class);
				registry.registerBeanDefinition(DzTransactionTask.class.getName(), autoConfigDef.getBeanDefinition());
			}
		}
	}
}
