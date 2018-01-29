package com.dazong.common.lock.config;

import com.dazong.common.lock.LockManager;
import com.dazong.common.lock.annotation.EnableDistrbutionLock;
import com.dazong.common.lock.aop.DistributionLockAspect;
import com.dazong.common.lock.impl.LockManagerImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.*;

/**
 * 基于SpringBoot的自动配置实现
 * @author Sam
 * @version 1.0.0
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ConditionalOnBean(annotation={EnableDistrbutionLock.class})
public class LockAutoConfiguration {

    @Bean
    public LockManager lockManager() {
        LockManagerImpl lockManager = new LockManagerImpl();
        return lockManager;
    }

    @Bean
    public DistributionLockAspect distributionLockAspect() {
        return new DistributionLockAspect();
    }

}
