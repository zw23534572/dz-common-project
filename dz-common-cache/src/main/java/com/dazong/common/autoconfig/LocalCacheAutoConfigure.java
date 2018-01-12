package com.dazong.common.autoconfig;


import com.dazong.common.cache.manager.CacheFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author DanielLi
 * @description Redis Cache自动化配置
 */
public class LocalCacheAutoConfigure extends AbstractAutoConfigure{
}
