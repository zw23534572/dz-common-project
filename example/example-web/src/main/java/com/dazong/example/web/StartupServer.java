package com.dazong.example;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.dazong.common.ApplicationInfo;
import com.dazong.common.annotation.EnableDzWeb;
import com.dazong.common.annotation.EnableValiadtor;

import com.dazong.common.support.monitor.SimpleMonitorServlet;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import javax.annotation.PreDestroy;

/**
 * @author huqichao
 */
@EnableTransactionManagement
@SpringBootApplication
@ImportResource(locations = { "classpath:application-bean.xml" })
@ComponentScan({ "com.dazong.example"})
@MapperScan("com.dazong.example.dao.mapper*")
@EnableDzWeb
@EnableValiadtor(patterns = { "com.dazong.example.service..*.*(..)" })
public class StartupServer {

	private Logger logger = LoggerFactory.getLogger(StartupServer.class);

	public static void main(String[] args) {
		System.setProperty("org.terracotta.quartz.skipUpdateCheck", "true");
		System.setProperty("dubbo.application.logger", "slf4j");
		SpringApplication.run(StartupServer.class, args);

	}

	@PreDestroy
	public void shutdown() {
		logger.info("Web server shutdown");
	}

	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor page = new PaginationInterceptor();
		page.setDialectType("mysql");
		return page;
	}

	@Bean
	public ApplicationInfo applicationInfo(){
		return new ApplicationInfo();
	}

	/**
	 * 初始化统一检测servlet
	 * 
	 * @return
	 */
	@Bean
	public SimpleMonitorServlet simpleMonitorServlet() {
		return new SimpleMonitorServlet();
	}

	/**
	 * 将统一检测servlet注入到spring boot中
	 * 
	 * @param simpleMonitorServlet
	 *            统一检测servlet
	 * @return
	 */
	@Bean
	public ServletRegistrationBean simpleMonitorServletRegistrationBean(SimpleMonitorServlet simpleMonitorServlet) {
		ServletRegistrationBean registration = new ServletRegistrationBean(simpleMonitorServlet);
		registration.setEnabled(true);
		registration.addUrlMappings("/simpleMonitor");
		return registration;
	}
}
