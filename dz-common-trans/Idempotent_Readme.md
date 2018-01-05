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
    1. mapper扫描路径增加：classpath:idempotent/mapper/*.xml
    
spring 4.2+:
>
    1. <bean class="com.dazong.common.idempotent.IdempotentAutoConfiguration" />
    2. mapper扫描路径增加：classpath:idempotent/mapper/*.xml
    
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