package com.dazong.common.lock.service;

import com.alibaba.fastjson.JSON;
import com.dazong.common.lock.LockProviderTypeEnum;
import com.dazong.common.lock.annotation.Locking;

/**
 * @author Sam
 * @version 1.0.0
 */
public class LockDemoService {

    @Locking(id = "'hehe'",module = "hello")
    public void hello(String hello) {
        System.out.println("hello");

    }

    @Locking(id = "'num'",module = "hello",waitTime = 1000)
    public void sayNum(Integer num) {
        System.out.printf("Thread:%s,Num:%d\n" , Thread.currentThread().getName() , num );
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Locking(id = "#hello",module = "hello",provider = LockProviderTypeEnum.REDIS)
    public void helloRedis(String hello,String name){
        System.out.println("hello,"+hello);
    }

    @Locking(id = "#simple.id",module = "simple",provider = LockProviderTypeEnum.REDIS)
    public void saveSimple(Simple simple) {
        System.out.println(JSON.toJSONString(simple) + "-------->is saved!");
    }

    public static class Simple {
        private String id;
        private String name;

        public Simple(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
