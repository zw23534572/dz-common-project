# 幂等组件
幂等：用户对于同一操作发起一次请求或者多次请求的结果应该是一致的。 


# 使用 
依赖： 
````
    <dependency>
        <groupId>com.dazong.common</groupId>
        <artifactId>dz-common-trans</artifactId>
        <version>x.x.x</version>
    </dependency>
````

spring boot:
>   
    1. 启动类上添加 @EnableItempotent
    
    
# 最佳实践
    ````
    @Service
    public class IdemService {
        @Idempotent("#fooReq.transId")
        public void biz(FooReq fooReq, String pa){
            System.out.println("========执行业务==========");
        }
    
        @Idempotent("(#fooReq.transId).concat('xx')")
        public void biz(FooReq fooReq, String pa){
            System.out.println("========执行业务==========");
        }
    
        @Idempotent("(#fooReq.transId).concat(#fooReq2.transId)")
        public FooReq biz2(FooReq fooReq, FooReq fooReq2){
            System.out.println("========执行业务==========");
            return fooReq;
        }
    }
    ````
    
# WARNING 
1. 幂等组件默认认为数据库是可用的，而且不会出现应用突然宕机导致代码只执行一半的情况。 
2. 被@Idempotent标记的方法，应该特别注意里面是否有RPC调用。 
    Trans:本地事务操作    Rpc:远程调用 
    Rpc1 -> Trans1   支持 （表示：被@Idempotent标记的方法，先做Rpc调用，再做本地事务操作）  
    Trans1 -> Rpc1  不支持  
    Rpc1 -> Trans1 -> Rpc2 不支持  
    
    以上不支持的场景，需要将@Idempotent标在本地事务方法之上。或者开发者在代码中使用业务数据来做幂等判断。 
