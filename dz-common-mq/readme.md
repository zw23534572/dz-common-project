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

6、配置分布式调度任务
在分布式调用任务系统配置调用 ReTryNotifyJob、ReTrySendJob 的 execute 方法
```java
@Autowired
private ReTryNotifyJob reTryNotifyJob;

@Autowired
private ReTrySendJob reTrySendJob;
...
reTryNotifyJob.execute();
reTrySendJob.execute();
```


### Release Note

#### 1.0
1、支持在线更新本系统的数据库表

2、发送可靠性

3、按照发送的时间顺序通知消费者