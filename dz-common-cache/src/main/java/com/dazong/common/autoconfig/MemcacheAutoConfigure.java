package com.dazong.common.autoconfig;

import com.dazong.common.cache.core.impl.MemcacheHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author: DanielLi
 * @date: 2018/1/12
 * @description:自动化配置Memcache框架
 */
@Configuration
@ImportResource("/META-INF/dz-common-cache.xml")
@ConditionalOnClass({MemcacheHandler.class})
public class MemcacheAutoConfigure {
}
