package com.dazong.common.lock;

import com.dazong.common.lock.service.LockDemoService;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;

/**
 * @author Sam
 * @version 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LockTestApplication.class})
public class LockTest {

    @Autowired
    LockManager lockManager;

    @Autowired
    LockDemoService lockDemoService;

    @Test
    public void testLock() {

        try( DistributionLock lock = lockManager.createLock("WR","10001")) {
            if (lock != null)
                lock.lock();
            System.out.println("I 'm get the lock ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConcurrency() {
        ExecutorService executor = Executors.newFixedThreadPool(4);

        for (int i = 0,  len = 6;i < len; i++ ) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    lockDemoService.sayNum(RandomUtils.nextInt());

                }
            });
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executor.shutdown();
    }

    @Test
    public void testAop() {
        lockDemoService.hello("XXXX");
    }

    @Test
    public void testAopRedis() {
        lockDemoService.helloRedis("XXXX","NIHao");
    }

    @Test
    public void testSaveSimple() {
        lockDemoService.saveSimple(new LockDemoService.Simple("1","simpl Name 1"));
    }
}
