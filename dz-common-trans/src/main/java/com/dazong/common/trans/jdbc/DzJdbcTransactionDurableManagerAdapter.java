package com.dazong.common.trans.jdbc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dazong.common.trans.DzTransactionDurableManger;
import com.dazong.common.trans.jdbc.mapper.DzTransactionObjectMapper;
import com.dazong.common.trans.support.DzTransactionObject;

/**
 * 事务jdbc持久化适配器
 * 
 * @author hujunzhong
 *
 */
@Component
public class DzJdbcTransactionDurableManagerAdapter implements DzTransactionDurableManger {

	@Autowired
	private DzTransactionObjectMapper mapper;

	@Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
	public void saveTransaction(DzTransactionObject transaction) {
		mapper.insertSelective(transaction);
	}

	public void updateTransaction(DzTransactionObject transaction) {
		mapper.updateByPrimaryKeySelective(transaction);
	}

	public void commitTransaction(String uid, boolean isSuccess) {
		if (isSuccess) {
			mapper.deleteByPrimaryKey(uid);
		} else {
			// 根事务失败,删除整个事务
			mapper.deleteByRid(uid);
		}
	}

	@Override
	public void prepareRetryTransaction(DzTransactionObject to) {
		List<String> offsprings = getAllOffspring(to);
		for (String uid : offsprings) {
			mapper.deleteByPrimaryKey(uid);
		}

	}

	@Override
	public void retryFail(DzTransactionObject updateTo) {
		List<String> offsprings = getAllOffspring(updateTo);
		for (String uid : offsprings) {
			mapper.deleteByPrimaryKey(uid);
		}

		mapper.updateByPrimaryKeySelective(updateTo);
	}

	@Override
	public List<DzTransactionObject> queryTimeoutTransactions(int retryBatchSize) {
		Map<String,Object> params = new HashMap<>(2);
		params.put("retryBatchSize", retryBatchSize);
		params.put("currentDateMsec", System.currentTimeMillis());
		return mapper.queryTimeoutTransactions(params);
	}

	@Override
	public void deleteByBussinessId(String bussinessId) {
		mapper.deleteByBussinessId(bussinessId);
	}

	@Override
	public List<DzTransactionObject> queryBussinessIdQueue() {
		return mapper.queryBussinessIdQueue();
	}

	private List<String> getAllOffspring(DzTransactionObject to) {
		List<DzTransactionObject> transactions = mapper.queryTransactionsByRid(to.getRid());
		if (transactions == null || transactions.size() <= 1) {
			return new ArrayList<>(0);
		}

		Map<String, List<String>> transactionTree = new HashMap<>(transactions.size());
		for (DzTransactionObject dzTransactionObject : transactions) {
			String pid = dzTransactionObject.getPid();
			if (pid == null) {
				continue;
			}

			List<String> brothers = transactionTree.get(pid);
			if (brothers == null) {
				brothers = new ArrayList<>();
				transactionTree.put(pid, brothers);
			}

			brothers.add(dzTransactionObject.getUid());
		}

		return getAllOffspring(to.getUid(), transactionTree);
	}

	/**
	 * 获取所有后代事务
	 * 
	 * @param uid
	 * @param transactionTree
	 * @return
	 */
	private List<String> getAllOffspring(String uid, Map<String, List<String>> transactionTree) {
		List<String> result = new ArrayList<>();
		List<String> childs = transactionTree.get(uid);
		if (childs != null) {
			for (String childid : childs) {
				result.addAll(getAllOffspring(childid, transactionTree));
			}

			result.addAll(childs);
		}

		return result;
	}
}
