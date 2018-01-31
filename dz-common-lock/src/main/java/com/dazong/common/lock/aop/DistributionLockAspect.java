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
import org.springframework.context.support.ApplicationObjectSupport;
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
public class DistributionLockAspect extends ApplicationObjectSupport {

    @Autowired
    LockManager lockManager;

    @Pointcut("@annotation(com.dazong.common.lock.annotation.Locking)")
    public void pointcut(){
        // 这里不执行任何逻辑，只是用于定义Pointcut
    }

    @Around(value="pointcut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        try(DistributionLock lock = createLock(proceedingJoinPoint)) {
            if (lock != null) {
                lock.lock();
            }
            return proceedingJoinPoint.proceed();
        }

    }

    /** 根据被拦截的方法参数创建一个分布式锁 */
    private DistributionLock createLock(JoinPoint jp) {
        LockInfo lockInfo = createLockID(jp);
        if (lockInfo != null) {
            return lockManager.createLock(lockInfo);
        }
        return null;
    }

    /** 根据被拦截的参数生成一个锁定义 */
    private LockInfo createLockID(JoinPoint joinPoint) {
        Class<? extends Object> targetClass = joinPoint.getTarget().getClass();
        String methodName                   = joinPoint.getSignature().getName();
        Object[] arguments                  = joinPoint.getArgs();
        Method method                       = ClassWrapper.wrap(targetClass).getMethod(methodName,Locking.class);
        if (method != null) {
            Locking locking = method.getAnnotation(Locking.class);

            if (locking.expiredTime() < locking.waitTime()) {
                throw new LockException("锁的失效时间不能小于等待时间!!!");
            }

            //构建 SPEL
            ExpressionParser parser           = new SpelExpressionParser();
            StandardEvaluationContext context =
                    new MethodBasedEvaluationContext(
                            joinPoint.getTarget(),method,arguments,new DefaultParameterNameDiscoverer());
            context.setBeanResolver(new BeanFactoryResolver(getApplicationContext()));

            //condition为true时不加锁
            if (StringsUtils.isNotBlank(locking.condition())) {
                boolean conditionValue = parser.parseExpression(locking.condition()).getValue(context,boolean.class);
                if (!conditionValue) {
                    return null;
                }
            }

            // 执行spel，获取 lock id 的值
            String lockId       = parser.parseExpression(locking.id()).getValue(context,String.class);
            if (StringsUtils.isBlank(lockId)) {
                throw new LockException(String.format("不能创建锁，获取不到要指定参数为'%s'的锁ID值",locking.id()));
            }

            return SimpleLockInfo.of(locking,lockId);
        }
        return null;
    }


}
