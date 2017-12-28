package com.dazong.common.trans.properties;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.dazong.common.trans.serialize.HessianSerialize;

/**
 * 事务配置
 * @author hujunzhong
 *
 */
@ConfigurationProperties(prefix="dz.transaction")
@Data
public class DzTransactionProperties {
	
	private final static int CPU_COUNT = Runtime.getRuntime().availableProcessors();
	
	/**
	 * 异步提交事务线程池大小,默认为cpu核数
	 */
	private int asyncCommitThreadSize = CPU_COUNT;
	
	/**
	 * 异步事务线程池大小,默认为cpu核数
	 */
	private int asyncTransactionThreadSize = CPU_COUNT;
	
	/**
	 * 事物管理器自定义的spring bean名称
	 */
	private String transactionManagerBeanName = "dzBaseTransactionManager";
	
	/**
	 * 每次重试的事务数
	 */
	private int retryBatchSize = 10;
	
	/**
	 * 事务重试毫秒数最小值.默认10s
	 */
	private long timeoutMinMs = 10 * 1000L;
	
	/**
	 * 定时任务cron表达式配置,默认3s执行一次
	 */
	private String taskCron = "0/3 * * * * ?";
	
	/**
	 * 参数序列化类,默认为com.dazong.transaction.serialize.JavaObjectSerialize.JavaObjectSerialize
	 */
	private String ParamSerializeClass = HessianSerialize.class.getName();
	
}
