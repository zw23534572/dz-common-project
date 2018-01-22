package com.dazong.example.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dazong.common.elasticjob.annotation.EjTask;
import com.dazong.common.trans.DzTransactionScheduler;
@EjTask(cron="0/3 * * * * ?")
public class TransJob implements SimpleJob{

	@Override
	public void execute(ShardingContext shardingContext) {
		DzTransactionScheduler.get().scheduleTask();
	}
}
