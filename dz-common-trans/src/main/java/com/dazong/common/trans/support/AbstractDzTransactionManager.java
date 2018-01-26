package com.dazong.common.trans.support;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dazong.common.trans.DzTransactionDurableManger;
import com.dazong.common.trans.DzTransactionException;
import com.dazong.common.trans.DzTransactionManager;
import com.dazong.common.trans.SerializeException;
import com.dazong.common.trans.SimpleThreadFactory;
import com.dazong.common.trans.TransactionDefinition;
import com.dazong.common.trans.TransactionStatus;
import com.dazong.common.trans.annotation.Propagation;
import com.dazong.common.trans.properties.DzTransactionProperties;
import com.dazong.common.trans.serialize.IParamSerialize;
import com.google.common.collect.Maps;

/**
 * 
 * @author hujunzhong
 *
 */
public abstract class AbstractDzTransactionManager implements DzTransactionManager {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	protected DzTransactionDurableManger transactionDurableManager;

	@Autowired
	private DzTransactionProperties dzTransactionConfig;

	protected ExecutorService executor;

	protected Map<String, DzTransactionDeclear> transactionDeclearMap;

	protected boolean usedFmeye;

	protected IParamSerialize paramSerialize;

	@PostConstruct
	public void init() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		int nThreads = this.dzTransactionConfig.getAsyncCommitThreadSize();
		executor = new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(), new SimpleThreadFactory());
		logger.info("初始化事务提交线程池,线程数:{}", nThreads);
		transactionDeclearMap = Maps.newHashMap();
		usedFmeye = isUsedFmeye();
		paramSerialize = (IParamSerialize) Class.forName(this.dzTransactionConfig.getParamSerializeClass())
				.newInstance();
	}

	public void registerTransaction(String name, Class<?> type, Method method, Object bean) {
		if (transactionDeclearMap.containsKey(name)) {
			logger.error("事务{}已存在,不能重复!", name);
			throw new DzTransactionException("事务" + name + "已存在,不能重复!");
		}

		transactionDeclearMap.put(name, new DzTransactionDeclear(name, type, method, bean));
	}

	public TransactionStatus begin(TransactionDefinition def) {
		DefaultTransactionStatus status = new DefaultTransactionStatus();
		status.setTransaction(true);

		Propagation pagation = def.getPropagation();
		if (pagation == null) {
			pagation = Propagation.REQUIRED;
		}

		boolean hasTransaction = hasTransaction();
		if (pagation == Propagation.INTERRUPT_NOT_NEW) {
			if (hasTransaction) {
				throw new DzTransactionException("INTERRUPT_NOT_NEW:已存在事务,不允许重入!");
			}
		} else if (pagation == Propagation.MANDATORY) {
			if (!hasTransaction) {
				throw new DzTransactionException("MANDATORY:不存在事务,不允许新建事务!");
			}
			status.setTransaction(false);
			return status;
		} else if (pagation == Propagation.REQUIRED && hasTransaction) {
			status.setTransaction(false);
			return status;
		}

		DzTransactionObject transaction = doGetTransactionObject(def);
		status.setTransactionObject(transaction);
		status.setNewTransaction(!hasTransaction);
		boolean isRetry = isRetry();
		status.setRetry(isRetry);
		if (!isRetry || hasTransaction) {
			saveTransactionObject(transaction);
		} else if (def.getMaxTryTimes() > 0 && transaction.getRetryTally() >= def.getMaxTryTimes()) {
			status.setRetryEnd(true);
		}

		return status;
	}

	public void commit(final TransactionStatus status, final boolean rootTransactionSuccess) {
		// 异步提交,减少对业务流程的影响
		docommit(status, rootTransactionSuccess);
//		executor.execute(new Runnable() {
//			public void run() {
//				docommit(status, rootTransactionSuccess);
//			}
//		});
	}

	/**
	 * 继续重试,更新下次重试时间和已重试次数
	 */
	public void continueRetry(TransactionStatus status) {
		DzTransactionObject transaction = status.getTransactionObject();
		if (status.isRetryEnd()) {
			// 重试已达到最大次数,重试失败
			doRetryFail(transaction, "重试已达到最大次数");
		} else {
			// 更新下次重试时间和已重试次数
			DzTransactionObject updateTo = new DzTransactionObject();
			updateTo.setUid(transaction.getUid());
			updateTo.setRetryTally(transaction.getRetryTally());
			updateTo.setRetryTime(transaction.getRetryTime());
			updateTo.setFmeyeId(transaction.getFmeyeId());
			transactionDurableManager.updateTransaction(updateTo);
		}
	}

	@Override
	public List<DzTransactionObject> queryTimeoutTransactions() {
		return transactionDurableManager.queryTimeoutTransactions(this.dzTransactionConfig.getRetryBatchSize());
	}

	@Override
	public void retryTransaction(DzTransactionObject to) {
		if (to == null) {
			return;
		}

		DzTransactionDeclear transactionDeclear = transactionDeclearMap.get(to.getTransactionName());
		if (transactionDeclear == null) {
			logger.error("事务已不存在:{}", to);
			doRetryFail(to, "事务已不存在", true);
			return;
		}

		prepareRetry(to);
		DzTransactionSyncManager.setRetryInfo(to);
		try {
			if (to.getParams() != null) {
				Class<?>[] parameterTypes = transactionDeclear.method.getParameterTypes();
				Object[] params = paramSerialize.deserialize(to.getParams(), parameterTypes);
				transactionDeclear.method.invoke(transactionDeclear.bean, params);
			} else {
				transactionDeclear.method.invoke(transactionDeclear.bean);
			}
		} catch (SerializeException e) {
			// 可能参数类型发生变化导致序列化参数失败
			logger.error("事务重试异常,反序列化参数失败:{}", e);
			doRetryFail(to, "事务重试异常,反序列化参数失败", true);
		} catch (IllegalAccessException e) {
			logger.error("事务重试异常", e);
			doRetryFail(to, "事务对应的执行方法改变", true);
		} catch (IllegalArgumentException e) {
			logger.error("事务重试异常", e);
			doRetryFail(to, "事务对应的执行参数改变", true);
		} catch (InvocationTargetException e) {
			logger.warn("事务" + to.getTransactionName() + "重试失败", e.getTargetException());
		} finally {
			DzTransactionSyncManager.clear();
		}
	}

	@Override
	public void bussinessFinished(String bussinessId) {
		DzTransactionObject transaction = new DzTransactionObject();
		transaction.setUid(genderTransactionId());
		transaction.setStatus(DzTransactionObject.STATUS_BUSSINESS_QUEUE);
		transaction.setBussinessId(bussinessId);
		transactionDurableManager.saveTransaction(transaction);
	}

	@Override
	public void scheduleBussinessFinished() {
		List<DzTransactionObject> list = transactionDurableManager.queryBussinessIdQueue();
		if (list != null) {
			for (DzTransactionObject bussinessIdObject : list) {
				transactionDurableManager.deleteByBussinessId(bussinessIdObject.getBussinessId());
			}
		}
	}

	protected void docommit(TransactionStatus status, boolean isSuccess) {
		try {
			DzTransactionObject transactionObject = status.getTransactionObject();
			transactionDurableManager.commitTransaction(transactionObject.getUid(), isSuccess);
		} catch (Exception e) {
			logger.error("提交事务异常", e);
		}
	}

	/**
	 * 获取新的事务对象
	 * 
	 * @param def
	 * @return
	 */
	protected abstract DzTransactionObject doGetTransactionObject(TransactionDefinition def);

	/**
	 * 重试失败,更新状态
	 * 
	 * @param transaction
	 */
	private void doRetryFail(DzTransactionObject transaction, String remark) {
		DzTransactionObject updateTo = new DzTransactionObject();
		updateTo.setUid(transaction.getUid());
		updateTo.setRid(transaction.getRid());
		updateTo.setRetryTally(transaction.getRetryTally());
		updateTo.setRetryTime(transaction.getRetryTime());
		updateTo.setStatus(DzTransactionObject.STATUS_FAIL);
		updateTo.setRemark(remark);
		updateTo.setFmeyeId(transaction.getFmeyeId());
		logger.error("事务重试失败,{}:{}", remark, transaction);
		transactionDurableManager.retryFail(updateTo);
	}

	/**
	 * 重试失败,重设fmeyeid,更新状态
	 * 
	 * @param transaction
	 * @param remark
	 * @param resetFmeye
	 */
	private void doRetryFail(DzTransactionObject transaction, String remark, boolean resetFmeye) {
		if (resetFmeye) {
			saveFmeyeId(transaction);
		}

		doRetryFail(transaction, remark);
	}

	/**
	 * 设置fmeyeid
	 * 
	 * @param transaction
	 */
	protected void saveFmeyeId(DzTransactionObject transaction) {
		if (usedFmeye) {
			com.dazong.eye.api.RpcPoint point = com.dazong.eye.api.TraceContext.currentPoint();
			if (point != null) {
				transaction.setFmeyeId(point.getTraceId());
			}
		}
	}

	/**
	 * 准备重试
	 * 
	 * @param to
	 */
	private void prepareRetry(DzTransactionObject to) {
		// 事务重试,清除掉原来的所有子事务.重试过程中会重新生成新的子事务.
		transactionDurableManager.prepareRetryTransaction(to);
	}

	/**
	 * 当前是否存在事务
	 * 
	 * @return
	 */
	protected boolean hasTransaction() {
		return DzTransactionSyncManager.getTransactionInfo() != null;
	}

	/**
	 * 当前事务是否是重试
	 * 
	 * @return
	 */
	protected boolean isRetry() {
		DzTransactionInfo txInfo = DzTransactionSyncManager.getTransactionInfo();
		if (txInfo != null) {
			return txInfo.getTransactionStatus().isRetry();
		} else {
			return DzTransactionSyncManager.getRetryInfo() != null;
		}
	}

	protected String genderTransactionId() {
		return UUID.randomUUID().toString();
	}

	protected void saveTransactionObject(DzTransactionObject transaction) {
		transactionDurableManager.saveTransaction(transaction);
	}

	public void setTransactionDurableManger(DzTransactionDurableManger transactionDurableManger) {
		this.transactionDurableManager = transactionDurableManger;
	}

	/**
	 * 是否使用fmeye
	 * 
	 * @return
	 */
	private boolean isUsedFmeye() {
		try {
			Class.forName("com.dazong.eye.api.TraceContext");
			logger.debug("使用飞马眼");
			return true;
		} catch (ClassNotFoundException e1) {
			logger.info("不使用飞马眼");
			return false;
		}
	}

	@SuppressWarnings("unused")
	private class DzTransactionDeclear {
		String name;
		Class<?> type;
		Method method;
		Object bean;

		public DzTransactionDeclear(String name, Class<?> type, Method method, Object bean) {
			this.name = name;
			this.type = type;
			this.method = method;
			this.bean = bean;
		}
	}
}
