package com.dazong.common.idempotent;

import com.dazong.common.idempotent.dao.IdempotentDao;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

/**
 * 幂等自动配置类
 * @author wzy on 2018/1/4.
 */
@Import(IdempotentAspect.class)
@MapperScan("com.dazong.common.idempotent.dao")
public class IdempotentAutoConfiguration implements InitializingBean {
    @Autowired
    private IdempotentDao idempotentDao;

    @Override
    public void afterPropertiesSet() throws Exception {
        idempotentDao.createTableIfNotExists();
    }
}
