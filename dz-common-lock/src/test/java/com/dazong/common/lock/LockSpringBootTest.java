package com.dazong.common.lock;

import com.dazong.common.lock.config.LockTestConfig;
import com.dazong.common.lock.service.LockAopService;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;

/**
 * @author Sam
 * @version 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LockManager.class,LockAopService.class})
@Import(LockTestConfig.class)
public class LockSpringBootTest {

    @Autowired
    LockManager lockManager;

    @Autowired
    LockAopService lockAopService;

    @Test
    public void testLock() {
        Lock lock = lockManager.createLock("WR","10001");
        try {
            lock.lock();
            System.out.println("I 'm get the lock ");
        } finally {
            lock.unlock();
        }
    }

    @Test
    public void testConcurrency() {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        for (int i = 0,  len = 3000;i < len; i++ ) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    lockAopService.sayNumber(RandomUtils.nextInt());

                }
            });
        }
        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executor.shutdown();
    }

    @Test
    public void testAop() {
        lockAopService.hello("XXXX");
    }

    @Test
    public void testAopRedis() {
        lockAopService.helloRedis("XXXX","NIHao");
    }

    @Test
    public void testSaveSimple() {
        lockAopService.saveSimple(new LockAopService.Simple("1","simpl Name 1"));
    }
}
