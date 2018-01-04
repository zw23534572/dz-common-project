package com.dazong.common.web.monitor;

import com.alibaba.dubbo.rpc.service.EchoService;
import com.dazong.common.monitor.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 提供检测接口
 * 
 * @author huqichao Created by on 17/5/8.
 *
 */
@WebServlet(name = "SimpleMonitor", urlPatterns = "/simpleMonitor")
public class SimpleMonitorServlet extends HttpServlet {

	private static final Logger logger = LoggerFactory.getLogger(SimpleMonitorServlet.class);

	private static List<BaseMonitor> monitorList;

	private static Class<?> clazz;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			StringBuilder sb = monitor();
			PrintWriter out = resp.getWriter();
			out.append(sb.substring(1));

			out.flush();
			out.close();

		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	private StringBuilder monitor() {
		StringBuilder sb = new StringBuilder();

		CheckResult result;
		long start = 0;
		for (BaseMonitor monitor : monitorList) {
			start = System.currentTimeMillis();
			result = monitor.check();
			result.setCost(System.currentTimeMillis() - start);
			sb.append("|").append(result.getName()).append("#").append(result.getStatus()).append("#")
					.append(result.getErrorMsg()).append("#").append(result.getCost());
		}

		startTimeMonitor(sb);

		return sb;
	}

	private void startTimeMonitor(StringBuilder sb) {
		try {
			sb.append("|").append(BaseMonitor.NAME_TIME).append("#").append(clazz.getField("time").get(clazz));
		} catch (Exception e) {
			logger.error("无法获取应用启动时间", e);
		}
	}

	@Override
	public void init() throws ServletException {
		monitorList = new ArrayList<>();

		try {
			clazz = Class.forName("com.dazong.eye.aspect.SpringServiceAspect");
			ServletContext servletContext = this.getServletContext();
			WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			jmsMonitor(context);
			redisMonitor(context);

			Map<EchoService, String> dubboConsumerMap = new HashMap<>();
			String dataSourceEqualsName = null;
			String dataSourceContainsName = null;
			String[] names = context.getBeanDefinitionNames();
			for (String name : names) {
				if (!name.contains(".")) {
					if ("datasource".equalsIgnoreCase(name)) {
						dataSourceEqualsName = name;
					}
					if (name.toLowerCase().contains("datasource")) {
						dataSourceContainsName = name;
					}
					try {
						EchoService service = (EchoService) context.getBean(name);
						dubboConsumerMap.put(service, name);
					} catch (ClassCastException e) {

					}
				}
			}

			if (dataSourceEqualsName == null) {
				dataSourceEqualsName = dataSourceContainsName;
			}

			if (dataSourceEqualsName != null) {
				DataSource dataSource = (DataSource) context.getBean(dataSourceEqualsName);
				monitorList.add(new DatabaseMonitor(dataSource));
			}

			monitorList.add(new DubboMonitor(dubboConsumerMap));
			monitorList.add(new VersionMonitor(null));

		} catch (Exception e) {
			logger.error("this application is not spring", e);
		}
	}

	private void redisMonitor(WebApplicationContext context) {
		try {
			Class.forName("org.springframework.data.redis.core.RedisTemplate");
			String[] redisBeanNames = context.getBeanNamesForType(RedisTemplate.class);
			if (redisBeanNames != null && redisBeanNames.length > 0) {
				RedisTemplate redisTemplate = context.getBean(redisBeanNames[0], RedisTemplate.class);
				monitorList.add(new RedisMonitor(redisTemplate));
			}
		} catch (Exception e) {
			logger.warn("this application is not RedisTemplate.{}", e.getMessage());
		}
	}

	private void jmsMonitor(WebApplicationContext context) {
		try {
			Class.forName("org.springframework.jms.core.JmsTemplate");
			String[] jmsBeanNames = context.getBeanNamesForType(JmsTemplate.class);
			if (jmsBeanNames != null && jmsBeanNames.length > 0) {
				JmsTemplate jmsTemplate = context.getBean(jmsBeanNames[0], JmsTemplate.class);
				monitorList.add(new MQMonitor(jmsTemplate));
			}
		} catch (Exception e) {
			logger.warn("this application is not JmsTemplate.{}", e.getMessage());
		}
	}
}
