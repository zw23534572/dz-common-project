package com.dazong.example.web;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.dazong.common.ApplicationInfo;
import com.dazong.common.annotation.EnableValiadtor;
import com.dazong.common.web.monitor.SimpleMonitorServlet;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PreDestroy;

/**
 * @author huqichao
 */
@EnableTransactionManagement
@SpringBootApplication
@ImportResource(locations = { "classpath:application-bean.xml" })
@ComponentScan({ "com.dazong.example", "com.dazong.common.aop" })
@MapperScan("com.dazong.example.dao.mapper*")
@EnableValiadtor(patterns = { "com.dazong.example.service..*.*(..)" })
//@EnableFeignClients
public class StartupServer {

	private Logger logger = LoggerFactory.getLogger(StartupServer.class);

	@Autowired
	private CuratorFramework curatorFramework;

	public static void main(String[] args) {
		System.setProperty("org.terracotta.quartz.skipUpdateCheck", "true");
		System.setProperty("dubbo.application.logger", "slf4j");
		SpringApplication.run(StartupServer.class, args);

	}

	@PreDestroy
	public void shutdown() {
		logger.info("Web server shutdown");
		curatorFramework.close();
	}

	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor page = new PaginationInterceptor();
		page.setDialectType("mysql");
		return page;
	}

	@Bean
	public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
		return new RestTemplate(factory);
	}
	
	@Bean
	public ApplicationInfo applicationInfo(){
		return new ApplicationInfo();
	}

	@Bean
	public CuratorFramework curatorFramework(@Value("${zk.host}") final String serverList) {
		CuratorFramework cf = CuratorFrameworkFactory.newClient(serverList, new RetryNTimes(10, 5000));
		cf.start();
		return cf;
	}

	@Bean
	public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setReadTimeout(5000);// ms
		factory.setConnectTimeout(15000);// ms
		return factory;
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
