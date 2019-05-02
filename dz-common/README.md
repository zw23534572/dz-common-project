# dz-common 

> 由基础工具类、异常处理、公用接口（ICache,ILock,@AutoRetry,@Locking,）、公共数据结构（缓存接口、、 方法返回 response）、公共验证

### 1、定义系统码（举例桃花：成功码20200，20就是系统码）

* application.properties

```properties
system_code=88
```
* 代码中使用

```java
public class StartupServer {
    public static void main(String[] args) {
		SpringApplication.run(StartupServer.class, args);
	}

    @Bean
	public ApplicationInfo applicationInfo(){
		return new ApplicationInfo();
	}
}

```


### 2、状态、异常、响应（response）对应关系



```java
//所有状态都基于IResultStatus接口
public interface IResultStatus  {
    /**
     * 获取枚举中定义的异常码
     * @return
     */
    public int getCode();

    /**
     * 获取枚举中定义的异常信息
     * @return
     */
	public String getMessage();
}

//common中公共定义
public enum CommonStatus implements IResultStatus {

	/**
	 * 参数异常
	 */
	ILLEGAL_PARAM(101, "参数[{0}]错误"),
	/**
	 * 成功
	 */
	SUCCESS(200, "成功"), 
	/**
	 * 处理失败
	 */
	FAIL(400, "处理失败！{0}"), 
	/**
	 * 系统错误
	 */
	ERROR(500, "系统错误！{0}")
}

//业务系统使用举例，例如helios
public enum ResultEnum implements IResultStatus {
	
	SUCCESS(150200,"成功"),
    FAIL(150500,"失败"),
    USER_IS_NOT_EXIST(150101,"用户不存在！")
	;
}


//直接使用异常包装状态，如果设置系统码为88，客户端响应为88500，未设置则为500
ApplicationException e=new ApplicationException(CommonStatus.ERROR);

//使用响应包装异常
CommonResponse response=new CommonResponse(e);

//使用响应包装状态码
CommonResponse response=new CommonResponse(CommonStatus.ERROR);


//业务系统中，基于业务系统中定义的状态枚举，抛出异常
throw new BusinessException(ResultEnum.SUCCESS);



```

### 3、给公用的状态拼接系统码

```java

CommonStatus.ERROR.getCode();//值是500
CommonStatus.ERROR.joinSystemStatusCode().getCode();//举例，如果系统码是88，值是88500

```

### 4、Valiadtor(基于JSR 349 Bean Validation 1.1，HibernateValidator实现)

> 如果使用参数验证，所有参数验证失败的状态码都是101,如果设置系统码则为88101，{"msg": "getUser.arg0.userId必须为null","code": 88101}

```java
//启用验证
@EnableValiadtor(patterns = { "com.dazong.example.service..*.*(..)" })
public class StartupServer {
    public static void main(String[] args) {
		SpringApplication.run(StartupServer.class, args);
	}
}

//实现验证
public interface UserService {

	public DataResponse<UserInfo> getUser(@NotNull String userId);
	
	public DataResponse<UserInfo> getUser(@Valid UserInfo userInfo);
}


```

