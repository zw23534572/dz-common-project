package com.dazong.common.elasticjob.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;

import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.dazong.common.elasticjob.properties.ElassticjobZkProperties;
/**
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * @author 贸易研发部：yanghui
 * @since 2018年2月1日
 */
@Configuration
@Import(ElassticjobZkProperties.class)
public class RegistryCenterConfigure {
	
	@Autowired
	private ElassticjobZkProperties elassticjobProperties;
	
	@Bean
	public CoordinatorRegistryCenter registryCenter(){
		ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration(this.elassticjobProperties.getZkServer(), this.elassticjobProperties.getNamespace());
		zookeeperConfiguration.setBaseSleepTimeMilliseconds(this.elassticjobProperties.getBaseSleepTimeMilliseconds());
		zookeeperConfiguration.setMaxSleepTimeMilliseconds(this.elassticjobProperties.getMaxSleepTimeMilliseconds());
		zookeeperConfiguration.setMaxRetries(this.elassticjobProperties.getMaxRetries());
		zookeeperConfiguration.setSessionTimeoutMilliseconds(this.elassticjobProperties.getSessionTimeoutMilliseconds());
		zookeeperConfiguration.setConnectionTimeoutMilliseconds(this.elassticjobProperties.getConnectionTimeoutMilliseconds());
		if(!StringUtils.isEmpty(this.elassticjobProperties.getDigest())){
			zookeeperConfiguration.setDigest(this.elassticjobProperties.getDigest());
		}
		CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(zookeeperConfiguration);
        regCenter.init();
        return regCenter;
	}

}
