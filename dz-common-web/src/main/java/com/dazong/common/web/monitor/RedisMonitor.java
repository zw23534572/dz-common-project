package com.dazong.common.web.monitor;

import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;

import com.dazong.common.monitor.CheckResult;
import com.dazong.common.monitor.BaseMonitor;

import java.util.concurrent.TimeUnit;

/**
 * 
 * @author huqichao
 * Created by  on 17/5/25.
 *
 */
public class RedisMonitor extends BaseMonitor {


    public RedisMonitor(Object obj) {
        super(obj);
    }

    /**
     * 检测
     *
     * @return 检测结果
     */
    @Override
    public CheckResult check() {
        CheckResult result = new CheckResult(NAME_REDIS);
        RedisTemplate redisTemplate = (RedisTemplate) obj;
        if(redisTemplate != null){
            try {
                @SuppressWarnings("unchecked")
                BoundValueOperations<String, Object> ope = redisTemplate.boundValueOps("monitor");
                ope.set("test");
                ope.expire(1, TimeUnit.SECONDS);
            } catch (Exception e) {
                logger.error("redis connection error", e);
                result.setSuccess(false);
                result.setStatus(ERROR);
                result.setErrorMsg("redis connection error");
            }
        }
        return result;
    }
}
