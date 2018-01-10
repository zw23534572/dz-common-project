package com.dazong.common.trans.base;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.dazong.common.trans.TransactionDefinition;
import com.dazong.common.trans.support.AbstractDzTransactionManager;
import com.dazong.common.trans.support.DzTransactionInfo;
import com.dazong.common.trans.support.DzTransactionObject;
import com.dazong.common.trans.support.DzTransactionSupport;
import com.dazong.common.trans.support.DzTransactionSyncManager;

/**
 * 
 * @author hujunzhong
 *
 */
@Lazy
@Component
@SuppressWarnings("serial")
public class DzBaseTransactionManager extends AbstractDzTransactionManager {

	@Override
	protected DzTransactionObject doGetTransactionObject(TransactionDefinition def) {
		DzTransactionObject result = new DzTransactionObject();
		DzTransactionInfo pTransactionInfo = DzTransactionSyncManager.getTransactionInfo();
		DzTransactionObject retryInfo = DzTransactionSyncManager.getRetryInfo();
		String uid = genderTransactionId();
		String pid = null;
		String rid = uid;
		String bussinissId = def.getBussinessId();
		int retryTally = 0;
		if(pTransactionInfo != null){
			pid = pTransactionInfo.getUid();
			rid = pTransactionInfo.getRid();
			bussinissId = DzTransactionSupport.getDefaultIfNull(bussinissId, pTransactionInfo.getTransactionObject().getBussinessId());
		}else if(retryInfo != null){
			uid = retryInfo.getUid();
			rid = retryInfo.getRid();
			retryTally = retryInfo.getRetryTally() + 1;
		}
		
		result.setUid(uid);
		result.setPid(pid);
		result.setRid(rid);
		result.setTransactionName(def.getName());
		result.setParams(paramSerialize.serialize(def.getParams()));
		result.setRetryTime(System.currentTimeMillis() + def.getTimeout());
		result.setRetryTally(retryTally);
		result.setBussinessId(bussinissId);
		saveFmeyeId(result);
		
		return result;
	}

}
