## 大宗可靠消息

---

### 简介
目前支持发送消息时的可靠性，通过发送时本地事务来保证可靠性。以及一定限度的按照发送时间顺序通知消费者

![时序图](https://git.dazong.com/TradeDept/dz-mq/uploads/1a61ee27d385bf8b42d18f4b103403ea/mq.png)

### 使用说明
1、在pom.xml文件引入dz-mq的jar
```xml
<!--配置dz-mq-->
<dependency>
    <groupId>com.dazong.common.mq</groupId>
    <artifactId>dz-common-mq</artifactId>
    <version>4.0.0-SNAPSHOT</version>
</dependency>

<!--配置elastic-job，如果已经依赖则不用配置-->
<dependency>
    <groupId>com.dangdang</groupId>
    <artifactId>elastic-job-lite-core</artifactId>
    <version>2.1.5</version>
</dependency>
<dependency>
    <groupId>com.dangdang</groupId>
    <artifactId>elastic-job-lite-spring</artifactId>
    <version>2.1.5</version>
</dependency>

<dependency>
    <groupId>org.apache.curator</groupId>
    <artifactId>curator-client</artifactId>
    <version>2.10.0</version>
</dependency>
<dependency>
    <groupId>org.apache.curator</groupId>
    <artifactId>curator-framework</artifactId>
    <version>2.10.0</version>
</dependency>
<dependency>
    <groupId>org.apache.curator</groupId>
    <artifactId>curator-recipes</artifactId>
    <version>2.10.0</version>
</dependency>
<dependency>
    <groupId>com.netflix.curator</groupId>
    <artifactId>curator-test</artifactId>
    <version>1.3.3</version>
    <scope>test</scope>
</dependency>
```

2、在spring配置文件加入：
```xml
<!--如果项目为非springboot，则需要再加载这个bean，是springboot项目，不需要配置这个-->
<bean id="mqAutoConfiguration" class="com.dazong.common.mq.MQAutoConfiguration" />
```

3、在应用properties的文件中配置应用数据库名
```properties
db.name=数据库名
```

4、发送消息
```java
@Autowired
private ActiveMQProducer producer;
...
DZMessage message = DZMessage.wrap("test11", "哈哈");
producer.sendMessage(message);
```
发送时必须保证与调用者存在同一个事务中，发送失败时将会报 MQException，所以事物回滚的异常要包含 MQException

5、接收消息
```java
@Subscribe(topic = "test11", name = "Pay")
public class PayMQ implements IMessageListener {
    @Override
    public void receive(String message) {
        //TODO
    }
}
```

6、配置ElasticJob任务
```java

@Resource
private ZookeeperRegistryCenter regCenter;

@Bean(initMethod = "init", destroyMethod = "close")
public ZookeeperRegistryCenter regCenter(@Value("${zk.host}") final String serverList,
                                             @Value("${elastic.job.zk.namespace}") final String namespace) {
    ZookeeperConfiguration configuration = new ZookeeperConfiguration(serverList, namespace);
    configuration.setConnectionTimeoutMilliseconds(3000);
    configuration.setSessionTimeoutMilliseconds(3000);
    return new ZookeeperRegistryCenter(configuration);
}

@Bean(initMethod = "init")
public JobScheduler registryReTrySendJob(ReTrySendJob reTrySendJob) {
    LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(
        JobCoreConfiguration.newBuilder(ReTrySendJob.class.getSimpleName(), "0 0/1 * * * ?", 1).build(),
        ReTrySendJob.class.getCanonicalName())).overwrite(true).build();
    return new SpringJobScheduler(reTrySendJob, regCenter, liteJobConfiguration);
}

@Bean(initMethod = "init")
public JobScheduler registryReTryNotifyJob(ReTryNotifyJob reTryNotifyJob) {
    LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(
        JobCoreConfiguration.newBuilder(ReTryNotifyJob.class.getSimpleName(), "0 0/5 * * * ?", 1).build(),
        ReTryNotifyJob.class.getCanonicalName())).overwrite(true).build();
    return new SpringJobScheduler(reTryNotifyJob, regCenter, liteJobConfiguration);
}
```


### Release Note

#### 1.0
1、支持在线更新本系统的数据库表

2、发送可靠性

3、按照发送的时间顺序通知消费者