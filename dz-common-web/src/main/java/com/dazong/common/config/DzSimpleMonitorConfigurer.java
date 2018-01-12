package com.dazong.common.config;

import com.dazong.common.annotation.EnableDzSimpleMonitor;
import com.dazong.common.support.monitor.SimpleMonitorServlet;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * 将统一检测servlet注入到spring boot中
 *
 * @return
 */
@ConditionalOnBean(annotation={EnableDzSimpleMonitor.class})
public class DzSimpleMonitorConfigurer {

    @Bean
    public SimpleMonitorServlet simpleMonitorServlet() {
        return new SimpleMonitorServlet();
    }


    @Bean
    public ServletRegistrationBean simpleMonitorServletRegistrationBean(SimpleMonitorServlet simpleMonitorServlet) {
        ServletRegistrationBean registration = new ServletRegistrationBean(simpleMonitorServlet);
        registration.setEnabled(true);
        registration.addUrlMappings("/simpleMonitor");
        return registration;
    }
}
