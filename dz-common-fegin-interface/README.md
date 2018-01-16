## 集成封装了php和pushapi等非java应用提供的http接口，方便spring boot应用调用

## 客户端接入

### 引入feignclient.jar，maven配置

```xml
<dependency>
    <groupId>com.dazong.feign.client</groupId>
    <artifactId>feignclient</artifactId>
    <version>最新版本</version>
</dependency>
```

### 调用客户端使用feign来作为http调用客户端，当前jar提供按需加载的功能，所以使用方不需要在注解@EnableFeignClients上去扫描包，只需要按照需要在application.properties文件加上相关配置即可，相关配置如下：

#### 1、需要用户相关信息接口添加以下配置

```
feignclient.userapi.serviceId=userapi
feignclient.userapi.url=http://userapi.dazong.com
```
代码就可以注入
```
@Autowired
private IUserInfoService userInfoService;
```

#### 2、需要pushapi验证U盾相关信息接口添加以下配置

```
feignclient.pushapi.serviceId=pushapi
feignclient.pushapi.url=http://pushapi.dazong.com:2005
```
代码就可以注入
```
@Autowired
private IPushapiService pushapiService;
```

#### 3、需要合同协议相关接口添加以下配置
```
feignclient.htapi.serviceId=htapi
feignclient.htapi.url=http://htapi.dazong.com
```
代码就可以注入
```
@Autowired
private IContractService contractService;
```

#### 4、需要权限相关接口添加以下配置
```
feignclient.auth.serviceId=auth
feignclient.auth.url=http://auth.dazong.com
```
代码就可以注入
```
@Autowired
private IAuthService authService;
```

#### 5、需要上传文件相关接口添加以下配置
```
feignclient.up.serviceId=up
feignclient.up.url=https://up.dazong.com/upload
```
代码就可以注入
```
@Autowired
private IUploadService upService;
```