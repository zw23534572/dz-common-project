package com.dazong.common.web.config;

import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * 将统一检测servlet注入到spring boot中
 *
 * @return
 */
@ServletComponentScan("com.dazong.common.web")
public class DzMonitorConfigurer {

}
