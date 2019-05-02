package com.dazong.common.trans;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dazong.common.trans.support.DzTransactionObject;

/**
 * 事务调度器,提供重试相关的操作
 * @author hujunzhong
 *
 */
public class DzTransactionScheduler {
	
	private static Logger logger = LoggerFactory.getLogger(DzTransactionScheduler.class);
	
	private static DzTransactionScheduler scheduler = new DzTransactionScheduler();
	
	private DzTransactionManager manager;
	
	private DzTransactionScheduler(){
	}
	
	/**
	 * 获取调度器单例
	 * @return
	 */
	public static DzTransactionScheduler get(){
		return scheduler;
	}

	public DzTransactionManager getManager() {
		return manager;
	}

	public void setManager(DzTransactionManager manager) {
		this.manager = manager;
	}
	
	/**
	 * 定时任务调度入口.
	 */
	public void scheduleTask(){
		if(this.manager == null){
			return;
		}
		scheduleBussinessFinished();
		retryTimeoutTransaction();
	}
	
	/**
	 * 调度所有处理业务完成任务
	 */
	private void scheduleBussinessFinished() {
		try{
			manager.scheduleBussinessFinished();
		}catch (Exception e) {
			logger.error("处理业务完成异常", e);
		}
	}

	/**
	 * 重试超时的事务
	 */
	private void retryTimeoutTransaction(){
		List<DzTransactionObject> transactions = manager.queryTimeoutTransactions();
		if(transactions == null || transactions.isEmpty()){
			return;
		}
		
		for (DzTransactionObject dzTransactionObject : transactions) {
			logger.debug("重试事务:{},{}",Thread.currentThread().getId(), dzTransactionObject.getTransactionName());
			try{
				manager.retryTransaction(dzTransactionObject);
			}catch (Exception e) {
				logger.error("事务重试异常:", e);
			}
		}
	}
	
	/**
	 * 业务处理完成,添加任务:删除与业务id关连的事务
	 * @param bussinessId
	 */
	public void bussinessFinished(String bussinessId){
		if(bussinessId != null){
			logger.debug("业务处理完成,添加任务:删除与业务id关连的事务,{}", bussinessId);
			manager.bussinessFinished(bussinessId);
		}
	}
}
