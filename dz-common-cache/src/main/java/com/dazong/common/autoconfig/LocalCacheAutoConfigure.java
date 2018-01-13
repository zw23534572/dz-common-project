package com.dazong.common.autoconfig;


import com.dazong.common.cache.core.impl.LocalCacheHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author DanielLi
 * @description Redis Cache自动化配置
 */
@Configuration
@ImportResource("/META-INF/dz-common-cache.xml")
@ConditionalOnClass({LocalCacheHandler.class})
public class LocalCacheAutoConfigure extends AbstractAutoConfigure{
}
