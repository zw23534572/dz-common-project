## 简介

dz-common-web是专门针对web入口进行封装的基础组件，让使用者尽量少配置就可以达到开箱即用。

目前有以下功能：

1.统一异常拦截

2.统一正常封包

3.项目检测

## 项目配置

### 配置1：```pom.xml```添加父类引用

一个单纯的maven项目，在```pom.xml```文件配置

```xml
 <parent>
        <groupId>com.dazong.pom</groupId>
        <artifactId>dz-dependencies</artifactId>
        <version>1.6-SNAPSHOT</version>
 </parent>
 
 目前版本为1.6-SNAPSHOT
```

其中``dz-dependencies``包含

``com.dazong.pom:dz-springboot-pom:jar``--支持spring-boot的配置

``com.dazong.common:dz-common-web:jar``--本篇专门讲解此jar包

``com.dazong.common:dz-common-util:jar``

``com.dazong.common:dz-common:jar``--大宗common jar包公共类

### 配置2：```application.properties```添加项目系统码

```xml
system_code=88 --项目系统码
```

### 配置3： 使用web异常拦截与正常封包，需加上@EnableDzWeb


配置完成，可通过```spring boot```方式正常启动.


### 使用1：统一异常拦截

测试代码

```java
/**
  * 业务系统异常
*/
@RequestMapping("/test2")
@ResponseBody
public List<String> test2() {
    throw new ArgumetException(500, "业务系统的空指针异常");
}
```

测试结果，拦截异常时code，不自动加system_code

```
{
	"msg":"业务系统的空指针异常",
	"code":500
}
```

> 访问http://localhost/test2 接口时，异常信息自动封装成json返回。

### 使用2：统一正常封包

测试代码

```java
/**
  * 返回正常的实体     
*/
@RequestMapping("/test")
@ResponseBody
public List<String> test() {
  List<String> stringList = new ArrayList<>();
  stringList.add("测试1");
  stringList.add("测试2");
  return stringList;
}
```

测试结果

```
{
	"code":88200,
	"data":[
		"测试1",
		"测试2"
	],
	"msg":"成功",
	"success":true
}
```

> LIst<String>对象会封装公司commonResponse对象输出。
>
> 这样好处是，数据封装的操作交给了dz-common-web.

### 使用3：项目检测

  - 心跳检测 http://localhost/simpleMonitor
  - 线程堆栈检测 http://localhost/threadDump



### 样例所有代码贴出，供参考

dz-common-web-example\src\main\java\com\dazong\test\StartupServer.java

```java
package com.dazong.test;

import com.dazong.common.web.annotation.EnableDzSimpleMonitor;
import com.dazong.common.web.annotation.EnableDzWeb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StartupServer {
    public static void main(String[] args) {
        SpringApplication.run(StartupServer.class, args);
    }
}
```

dz-common-web-example\src\main\java\com\dazong\test\web\ExcepitionWebController.java

```java
package com.dazong.test.web;

import com.dazong.common.exceptions.ArgumetException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ExcepitionWebController {

    /**
     * 统一正常封包
     */
    @RequestMapping("/test")
    @ResponseBody
    public List<String> test() {
        List<String> stringList = new ArrayList<>();
        stringList.add("测试1");
        stringList.add("测试2");
        return stringList;
    }

    /**
     * 统一异常拦截
     */
    @RequestMapping("/test1")
    @ResponseBody
    public List<String> test1() {
        throw new ArgumetException(500, "业务系统的空指针异常");
    }
}
```

dz-common-web-example\src\main\resources\application.properties

```java
system_code=88  项目code
```