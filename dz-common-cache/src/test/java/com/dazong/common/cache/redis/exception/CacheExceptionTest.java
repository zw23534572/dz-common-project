package com.dazong.common.cache.redis.exception;

import com.dazong.common.autoconfig.RedisAutoConfigure;
import com.dazong.common.cache.exception.CacheException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: DanielLi
 * @date: 2018/1/25
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisAutoConfigure.class)
@EnableAutoConfiguration
public class CacheExceptionTest {

    @Test
    public void cacheExeptionTest(){
        CacheException exception = new CacheException("哈哈异常");
        assert (exception instanceof RuntimeException);
        assert (exception instanceof CacheException);
    }
    @Test
    public void cacheExeptionTest2(){
        CacheException exception = new CacheException("哈哈异常", null);
        assert (exception instanceof RuntimeException);
        assert (exception instanceof CacheException);
    }
}
