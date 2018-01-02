package com.dazong.common.aop;

import com.alibaba.fastjson.JSON;
import com.dazong.common.CommonStatus;
import com.dazong.common.exceptions.BusinessException;
import com.dazong.common.resp.CommonResponse;
import com.dazong.common.resp.DataResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * web层的aop处理
 * @author jianhao84
 *
 */
@Aspect
@Component
@Order(Integer.MIN_VALUE)       // 在飞马眼后拦截
public class DazongAopHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around(value = "execution(* com.dazong.common.aop.DazongAop+.*(..))")
    public Object handle(ProceedingJoinPoint pjp) throws Throwable {
        if(logger.isDebugEnabled()){
            doBefore(pjp);
        }

        ResultCode resultCode = pjp.getTarget().getClass().getAnnotation(ResultCode.class);
        Object retVal;
        try {
        	// 接口返回对象的内容
            Object returnValue = pjp.proceed(); 
            // 如果返回的已经是包装类(是CommonResponse子孙类),则直接返回
            if (returnValue != null && CommonResponse.class.isAssignableFrom(returnValue.getClass())) {
                retVal = returnValue;
            }else {
            	// 如果不是包装类,则包装后返回给前端
                if(resultCode != null) {
                    retVal = new DataResponse<>(resultCode.success(),"处理成功",returnValue);
                }else {
                    retVal = new DataResponse<>(returnValue);
                }
            }
        } catch (Throwable exception) {
            handleExceptionLog(pjp, exception);
            // 如果异常是大宗基础异常类的子孙类
            if(BusinessException.class.isAssignableFrom(exception.getClass())) {
                BusinessException resultException = (BusinessException)exception;
                retVal = new CommonResponse(resultException);
            }else {
                if(resultCode != null) {
                    retVal = new CommonResponse(resultCode.fail(), "处理失败");
                }else {
                    retVal = new CommonResponse(CommonStatus.ERROR);
                }
            }
        }

        if(logger.isDebugEnabled()) {
            doAfterReturning(retVal);
        }
        return retVal;
    }

    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 记录下请求内容
        logger.info("请求方法: {}, 请求参数: {}",
                joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName(),
                JSON.toJSONString(joinPoint.getArgs()));
    }

    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容，json转换之后输出到日志中
        logger.info("返回参数 : " + JSON.toJSONString(ret));
    }

    private void handleExceptionLog(ProceedingJoinPoint pjp, Throwable exception) {
        logger.error("异常信息:", exception);
        try {
            logger.error("请求方法: {}, 请求参数: {}",
                    pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName(),
                    JSON.toJSONString(pjp.getArgs()));
        } catch (Exception e) {
            logger.error("handleExceptionLog", e);
        }
    }
}
