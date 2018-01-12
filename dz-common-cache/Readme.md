## 大宗缓存框架

---

### 简介
缓存框架，目前支持Redis缓存和本地缓存

时序图

### 使用说明
1、在pom.xml文件引入dz-common-cache
```xml
<!--配置dz-common-cache-->
<dependency>
    <groupId>com.dazong.common.cache</groupId>
    <artifactId>dz-common-cache</artifactId>
    <version>4.0.0-SNAPSHOT</version>
</dependency>
```

2、在spring配置文件加入：
```xml
<!--如果项目为非springboot，则需要再加载这个bean，是springboot项目，不需要配置这个-->
  <bean id="redisAutoConfigure" class="com.dazong.common.autoconfig.RedisAutoConfigure"></bean>
  ```
  

4、使用方式
```java
@Autowired
CacheFactory cacheFactory;
```
```java
    @Test
    public void testDelete() {
        List<String> strs2 = new ArrayList<String>();
        strs2.add("1");
        strs2.add("2");
        cacheFactory.getCacheHandler(CacheType.CACHE_REDIS).delete("test:list:string");
        }
```


### Release Note

#### 1.0
- 支持Redis缓存框架
- 支持本地缓存框架
- 支持spring boot自动注入和非spring boot项目