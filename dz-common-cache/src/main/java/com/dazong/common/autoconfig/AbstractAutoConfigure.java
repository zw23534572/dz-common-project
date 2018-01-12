package com.dazong.common.autoconfig;

import com.dazong.common.cache.manager.CacheFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author: DanielLi
 * @date: 2018/1/12
 * @description:
 */
@Configuration
@ConditionalOnClass({CacheFactory.class})
@ImportResource("/META-INF/dz-common-cache.xml")
public class AbstractAutoConfigure {
}
