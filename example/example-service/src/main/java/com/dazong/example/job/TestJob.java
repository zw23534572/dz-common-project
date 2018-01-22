package com.dazong.example.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dazong.common.elasticjob.annotation.EjTask;
//@EjTask(cron="0/5 * * * * ?")
public class TestJob implements SimpleJob {

	@Override
	public void execute(ShardingContext shardingContext) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("TestJob：" + sdf.format(new Date()));
	}
}
