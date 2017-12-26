# 原则
> 项目标准化，工作自动化

# 无争议
* 分布式文件服务：fastdfs
* 分布式配置：disconf
* 分布式跟踪：飞马眼
* 分布式日志：飞马眼
* 分布式调度框架：elastic job
* 微服务网关：apigateway（基于zuul）
* 业务监控：飞马眼
* 代码质量工具：sonarqube

# 讨论
* jdk：1.7 or 1.8
* 应用框架:spring framework+ spring boot
* RPC+服务治理：dubbo  or spring cloud ？？？？
* 日志：程序里面全部引用slf4j，实现则使用log4j2
* 事件框架：dz-mq
* 分布式锁：优先使用zk实现锁，更稳当，基于注解实现（类仓储现在实现的@Locking(id="#breakOrderAddDto.receiptNo",module= ZkLockConstants.RECEIPT_PREFIX_LOCK)）
* 最终一致性事务补偿：最终一致性应该包含客户端重试和服务端幂等两块功能，DzTransaction（是否有必要把注解改名叫@AutoRetry），幂等工具：？？？共用业务幂等组件
* 异常处理:dz-common-util 包下的几个异常类基础上封装，原则:非第三方调用直接抛异常，第三方调用则封装异常码返回（前台研发部算第三方）
* http访问：只要条件允许，全部封装为fegin-client方式调用，其次再选RestTemplate
* 缓存：redis （目前发现有单机方式redis，有集群方式访问redis集群，全部改为访问redis集群），封装？？？
* 序列化：fastjson or jackson？？？ 有必要封装吗？总觉得有点过度封装
* web：后端spring mvc , js框架？？？
* 标准项目模板：多模块项目、auto-create-project，https://git.dazong.com/TradeDept/auto-create-project
* 第三方公共库：apache commons？？google guava？？spring framework自带的一些工具类， 应该优先使用已经存在的工具类，先找spring framework，再找commons、guava，最后再自己实现。
* 公用继承pom
* 扩展工具mybatisplus&lombox

# 代码重组

> 分成大量模块，多人负责， 

```
dz-pom(基础pom)
    -->da-common-parent(common pom，方便编译)
       -->dz-common(基础结构，jar)
          -->dz-common-trans(jar)
          -->dz-common-lock(jar)
```
> dz-common之下的子包不能相互依赖，举例如果dz-common-trans需要使用lock，只能从dz-common中增加一个lock接口，最终由dz-common-lock实现，但dz-common-trans项目不能直接依赖dz-common-lock

```
1、dz-common  基础结构

由基础工具类、异常处理、公用接口（ICache,ILock,@AutoRetry,@Locking,）、公共数据结构（缓存接口、、 方法返回 response）、公共验证

以dz-common-util 为主，吸收cc-commons-core的功能

2、dz-common-trans  分布式事务
重组之前的dz-universal-chain和DzTransaction项目，完整的最终一致性，应该包含服务端幂等和客户端重试。


3、dz-common-lock  分布式锁
以cc-commons-lock为蓝本

4、dz-common-mq  可靠消息封装
以dz-mq 为蓝本

5、dz-common-fileserver  统一的分布式文件访问接口封装
考虑到我们走的是分布式、微服务路线，服务端操作文件，应该全部是分布式文件，
但当前环境，java项目没有直接去操作文件，所有直接以仓储cc-commons-fileserver（实现了fastdfs文件操作）为蓝本吸收

6、dz-common-fegin-interface 分装可以在多系统共用的http接口，例如请求php的用户信息等
以 https://git.dazong.com/TradeDept/feginclient 为蓝本


7、dz-common-web web封装
整合dz-common-web和cc-commons-web 有用的功能

8、dz-common-cache
整合相关缓存功能

9、公用继承pom
以https://git.dazong.com/platform/dz-parent 为蓝本

10、dz-common-elasticjob-starter 封装elasticjob通过spring starter方式调用
https://git.dazong.com/TradeDept/elasticjob-spring-boot-starter


```
