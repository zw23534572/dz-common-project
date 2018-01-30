package com.dazong.common.lock.aop;

import com.dazong.common.lock.DistributionLock;
import com.dazong.common.lock.LockException;
import com.dazong.common.lock.LockInfo;
import com.dazong.common.lock.LockManager;
import com.dazong.common.lock.annotation.Locking;
import com.dazong.common.lock.impl.SimpleLockInfo;
import com.dazong.common.util.StringsUtils;
import com.dazong.common.util.reflect.ClassWrapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

/**
 * 基于aop的分布式锁实现，解决在JDBC事务提交后再释放，而且不需要所有开发在代码写死锁的逻辑
 *
 * @author Sam
 * @version 1.0.0
 */
@Aspect
@Order(3)
public class DistributionLockAspect implements ApplicationContextAware {

    @Autowired
    LockManager lockManager;

    ApplicationContext applicationContext;

    @Pointcut("@annotation(com.dazong.common.lock.annotation.Locking)")
    public void pointcut(){}

    @Around(value="pointcut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        try(DistributionLock lock = createLock(proceedingJoinPoint)) {
            if (lock != null)
                lock.lock();
            Object result = proceedingJoinPoint.proceed();
            return result;
        }

    }

    private DistributionLock createLock(JoinPoint jp) {
        LockInfo lockInfo = createLockID(jp);
        if (lockInfo != null)
            return lockManager.createLock(lockInfo);
        return null;
    }

    private LockInfo createLockID(JoinPoint joinPoint) {
        Class<? extends Object> targetClass = joinPoint.getTarget().getClass();
        String methodName                   = joinPoint.getSignature().getName();
        Object[] arguments                  = joinPoint.getArgs();
        Method method                       = ClassWrapper.wrap(targetClass).getMethod(methodName,Locking.class);
        if (method != null) {
            Locking locking = method.getAnnotation(Locking.class);

            //构建 SPEL
            ExpressionParser parser           = new SpelExpressionParser();
            StandardEvaluationContext context =
                    new MethodBasedEvaluationContext(
                            joinPoint.getTarget(),method,arguments,new DefaultParameterNameDiscoverer());
            context.setBeanResolver(new BeanFactoryResolver(applicationContext));

            //condition为true时不加锁
            if (StringsUtils.isNotBlank(locking.condition())) {
                boolean conditionValue = parser.parseExpression(locking.condition()).getValue(context,boolean.class);
                if (!conditionValue)
                    return null;
            }

            String lockId       = parser.parseExpression(locking.id()).getValue(context,String.class);
            if (StringsUtils.isBlank(lockId)) {
                throw new LockException(String.format("不能创建锁，获取不到要指定参数为'%s'的锁ID值",locking.id()));
            }
            LockInfo lockInfo   = SimpleLockInfo.of(locking,lockId);
            return lockInfo;
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
