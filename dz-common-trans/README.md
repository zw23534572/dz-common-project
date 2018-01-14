## dz-common-trans

基于可补偿业务的重试，保证数据的最终一致性

## 使用说明

目前使用了springboot的相关特性，故只适应springboot项目适应，如果需要在普通spring项目使用需要兼容回来

### 引入

* maven配置中引入jar

```xml
<dependency>
    <groupId>com.dazong.common</groupId>
    <artifactId>dz-common-trans</artifactId>
    <version>官方发布最新版本</version>
</dependency>
```

* 在springboot启动主类上加上开启注解

```java
@SpringBootApplication
@EnableAutoRetry
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

### 代码使用

@AutoRetry

事务注解AutoRetry,带有此注解的方法会启用事务,如果抛出了需要retry的异常后会有服务去不断的重试这个方法直到成功,保证数据最终一致性.如下:

```java
@AutoRetry
public void testBussinessIdAnno(@BussinessIdParam("bussinessId") DzTransactionObject o, String s){
    System.out.println("执行测试业务参数注解:" + o.getBussinessId());
    throw new TestException(o.getBussinessId());
}
```

AutoRetry属性:

* name:事务名,不能重复.可为空,默认为当前的方法全名
* propagation:事物嵌套时处理方法,默为Propagation.REQUIRED.详细见Propagation
* retryFor: 需要重试的异常,默认为runtimeexception和error
* commitFailFor:业务正常处理完成返回失败的异常类型.不会重试
* timeout:重试超时间时间,超过这个时间没有commit重试,单位ms.默认5分钟
* maxTryTimes:最大重试次数,为0时不限
* async:是否异步执行,默认false

@BussinessIdParam

事务关连的业务唯一id注解.该注解应用在服务方法的参数上

* 如果被注解的参数是基本类型或string,直接取该参数为bussinessid
* 如果是map类型,取该map中key等于注解的value的值.
* 如果是其它object类型,取该object中对应field的值
* 如果是事务嵌套,子事务没有配置BussinessIdParam,则继承父事务的bussinessid

Propagation

* REQUIRED:如果当前存在事务,加入当前事务,不存在时新建事务
* MANDATORY:如果已经存在一个事务，支持当前事务。如果没有一个活动的事务，则抛出异常。
* INTERRUPT_NOT_NEW:如果当前已经存在事务,抛出异常中断.不存在时新建事务
* NESTED:如果当前存在事务,加入当前事务并创建子事务,根事务处理失败(即产生了commitFailFor异常)后,子事务不会重试.根事务成功,子事务失败,重试子事务.当前不存在,新建事务。

DzTransactionScheduler

事务调度器,提供重试相关的操作

* scheduleTask:定时任务调度入口:调度所有处理业务完成任务和重试超时的事务.此方法提供了重试的入口,各个业务使用各自的分布式任务调度系统定时执行此方法,如:

```java
public void initWorker(){
    thread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                DzTransactionScheduler.get().scheduleTask();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });
    thread.start();
}
```

* bussinessFinished:业务处理完成,添加任务:删除与业务bussinessId(见@BussinessIdParam使用)关连的事务;实现删除的动作由scheduleTask完成.比如:付款重试的业务,当违约处理完成后调用此方法中止掉相关的事务重试。

### 事务全局配置

配置是基于dz-common中的PropertiesAccessUtil实现的,可以取到加载到spring中的properties文件内容.

* dz.transaction.commit.thread.size:异步提交事务线程池大小,默认为cpu核数
* dz.transaction.async.thread.size:异步事务线程池大小,默认为cpu核数
* dz.transaction.manager.bean.name:事物管理器自定义的spring bean名称,默认dzBaseTransactionManager
* dz.transaction.retry.batch.size:每次重试的事务数,默认为10
* dz.transaction.timeout.min:事务重试毫秒数最小值.默认10s
* dz.transaction.task.cron:定时任务cron表达式配置,当应用中含有dztask的包时会自动使用dztask开启定时任务重试.默认:0/3 * * * * ?
* dz.transaction.param.serialize.class:参数序列化类,默认为com.dazong.transaction.serialize.HessianSerialize.目前dz-transaction支持json,hession,java三种序列化方式

### 注意事项

* 因为本方案是用重试来保证数据的一致性的,因此带有@AutoRetry的方法必须是幂等的.
* 由于是基于spring aop实现的,调用带有@AutoRetry的方法必须经过spring的切面,如下事务是不起做用的:

```
@Component
public class TransactionEntity {
    @AutoRetry
    public void testDoTransaction(Integer i, String s) {
        ....       
    }
    public void test(){
        this.testDoTransaction(1, "1");
    }
}
```

* 由于带有@DzTransactional的方法会把参数都序列化成json存入mysql,目前该列的长度为2048,所以参数不要传入超大对象

### 相关设计原理

* [分布式系统数据一致性问题](https://git.dazong.com/TradeDept/DeptDoc/blob/master/%E6%8A%80%E6%9C%AF%E7%A0%94%E7%A9%B6%E6%B1%A0/%E5%88%86%E5%B8%83%E5%BC%8F%E7%B3%BB%E7%BB%9F%E6%95%B0%E6%8D%AE%E4%B8%80%E8%87%B4%E6%80%A7%E9%97%AE%E9%A2%98.md)
* [dz-transaction设计](https://git.dazong.com/TradeDept/DeptDoc/blob/master/%E6%8A%80%E6%9C%AF%E7%A0%94%E7%A9%B6%E6%B1%A0/dz-transaction%E8%AE%BE%E8%AE%A1.md)