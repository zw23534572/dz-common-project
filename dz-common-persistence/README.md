# 简介 | Intro
> Mybatis 增强工具包 - 只做增强不做改变，简化CRUD操作。采用了mybatis -plus 的核心思想构建。

# 项目特性
* 无侵入：Mybatis-Plus 在 Mybatis 的基础上进行扩展，只做增强不做改变，引入 Mybatis-Plus 不会对您现有的 Mybatis 构架产生任何影响，而且 MP 支持所有 Mybatis 原生的特性
* 依赖少：仅仅依赖 Mybatis 以及 Mybatis-Spring
* 损耗小：启动即会自动注入基本CURD，性能基本无损耗，直接面向对象操作
* 通用CRUD操作：内置通用 Mapper、通用 Service，仅仅通过少量配置即可实现单表大部分 CRUD 操作，更有强大的条件构造器，满足各类使用需求
* 支持代码生成：采用代码或者 Maven 插件可快速生成 Mapper 、 Model 层代码，支持模板引擎.

# 支持的方法
* T selectById(Serializable id);                              //通过id查找
* List<T> selectBatchIds(List<? extends Serializable> idList); //查询（根据ID 批量查询）
* T selectOne(T t); //通过实体类查找单个
* List<T> selectList(T t); //通过实体类查询多个
* int selectCount(T t); //通过实体类，返回查询个数
* int insert(T bean); //插入
* int updateById(T bean); //更新
* int deleteById(Serializable id); //根据主键删除
* int deleteBatchByIds(List<? extends Serializable> idList); //删除（根据ID 批量删除）

# mybatis plus 的区别

| 区别             | dz-dazong-persistence  | mybatis plus |
| ---------------- | ---------------------- | ------------ |
| 热加载           | 不支持                 | 支持         |
| ActiveRecord     | 不支持                 | 支持         |
| 支持代码生成     | 支持部分service,mapper | 支持         |
| 分页插件         | 使用PageHelper         | 支持         |
| 内置性能分析插件 | 不支持                 | 支持         |
| 内置全局拦截插件 | 不支持                 | 支持         |

# 项目配置

## 配置1：```pom.xml```

一个单纯的maven项目，在```pom.xml```文件配置

```xml
 <!--parent 引用-->
 <parent>
     <groupId>com.dazong.pom</groupId>
     <artifactId>dz-dependencies</artifactId>
     <version>1.6-SNAPSHOT</version>
 </parent>
 
 <!--声明使用 dz-common-persistence-->
 <dependency>
     <groupId>com.dazong.common</groupId>
     <artifactId>dz-common-persistence</artifactId>
 </dependency>
```

 ## 配置2：```将自定义配置文件，加入对应的数据源```
 ```xml
    <!--自定义配置文件-->
    <bean id="myConfiguration" class="com.dazong.persistence.core.MyConfiguration"/>

    <!--常规配置-->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <property name="configuration" ref="myConfiguration"/> <!--自定义配置文件-->
        <property name="mapperLocations" value="classpath:mapper/*.xml"/>
    </bean>
    
    <!--常规配置-->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
          <property name="basePackage" value="com.dazong.persistence.mybatis.mapper"/>
          <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
    </bean>
 ```
 > 配置完成.



# 开始使用

## 定义实体类
```xml
package com.dazong.persistence.mybatis.entity;

@TableName("user")
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 用户主键
     */
    @TableId
    private Long id;
    /**
     * 用户姓名
     */
    @TableField("name")
    private String name;
    /**
     * 用户姓名_扩展
     */
    @TableField("ext_name")
    private String extName;
    /**
     * 状态：0:创建中 1：创建成功， 2:创建失败
     */
    @TableField("status")
    private Integer status;
    /**
     * 用户性别
     */
    @TableField("age")
    private Integer age;
}

```
## 定义mapper类
```xml
package com.dazong.persistence.mybatis.mapper;

import com.dazong.persistence.core.BaseMapper;
import com.dazong.persistence.mybatis.entity.User;

public interface UserMapper extends BaseMapper<User> {

}

```

