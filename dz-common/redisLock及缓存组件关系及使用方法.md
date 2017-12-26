## 关系类图
![image](http://git.dazong.com/platform/common.dazong.com/raw/dev/dz-common-util/redis_class_diagram.PNG)

## 使用条件
1. 使用了该组件的应用引用了jedis, spring-data-redis相关依赖。
2. 配置了相关redis配置信息。虽然代码中有默认值，但是由于服务器信息改变，许多默认值已经失效。故应用配置信息中并需配置redis相关连接信息。

## 使用方法
- 正常情况下，使用redis缓存信息，通过调用CacheUtil类中提供的方法去进行缓存操作
- 使用redis作为分布式锁时，使用RedisReentrantLock进行锁相关操作。




> **该类图只列出了对外提供功能的属性及方法，私有方法未列出。代码细节请直接查看源码**