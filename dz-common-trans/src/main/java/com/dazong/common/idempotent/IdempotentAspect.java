package com.dazong.common.idempotent;

import com.alibaba.fastjson.JSON;
import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.SerializerFactory;
import com.dazong.common.idempotent.dao.IdempotentDao;
import com.dazong.common.idempotent.domain.Idempotent;
import com.dazong.common.idempotent.exception.IdempotentErrors;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author wzy on 2018/1/4.
 */
@Aspect
@Component
public class IdempotentAspect {
    private static final Logger logger = LoggerFactory.getLogger(IdempotentAspect.class);
    private static final String STATUS_PROCESSING = "PROCESSING";
    private static final String STATUS_SUCCESS = "SUCCESS";
    private static final String STATUS_FAIL = "FAIL";

    @Autowired
    IdempotentDao idempotentDao;


    @Around("@annotation(com.dazong.common.idempotent.Idempotent)")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        String idempotentId = getIdempotentId(pjp);
        ResultHolder resultHolder = getLastSuccessResult(idempotentId);
        if (resultHolder != null) {
            return resultHolder.getResult();
        }

        try {
            Object result = pjp.proceed();
            serializeResult(result, idempotentId);
            return result;
        } catch (Throwable e) {
            logger.error("IdempotentAspect - biz execute error...", e);
            String errMsg = Strings.nullToEmpty(e.getMessage());
            String remark = "业务失败:" + (errMsg.length() > 120 ? errMsg.substring(0, 120) : errMsg);
            idempotentDao.updateStatus(idempotentId, STATUS_FAIL, STATUS_PROCESSING, null, null, null, remark);
            throw e;
        }
    }

    /**
     * 默认使用JSON 和 HESSION 序列化幂等结果到 DB，反序列化时使用 HESSION
     * @param rlt
     * @param idempotentId
     */
    private void serializeResult(Object rlt, String idempotentId) {
        // 返回值为null || void方法
        if(rlt == null){
            idempotentDao.updateStatus(idempotentId, STATUS_SUCCESS, STATUS_PROCESSING, null, null, null, null);
            return;
        }


        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Hessian2Output h2o = new Hessian2Output(os);
        try {
            h2o.writeObject(rlt);
            h2o.flushBuffer();
        } catch (IOException e) {
            throw IdempotentErrors.IDENPOTENT_ERROR.exp(e);
        }
        try {
            idempotentDao.updateStatus(idempotentId, STATUS_SUCCESS, STATUS_PROCESSING, JSON.toJSONString(rlt), os.toByteArray(), rlt.getClass().getName(), null);
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                logger.error("IdempotentFilter - ByteArrayOutputStream close error", e);
            }
        }
    }

    private ResultHolder getLastSuccessResult(String idempotentId) {
        logger.info("IdempotentAspect start... idempotentId={}", idempotentId);
        Idempotent idempotent = idempotentDao.selectOne(idempotentId);
        logger.info("IdempotentAspect - last time idempotent is : {}", JSON.toJSONString(idempotent));
        // 查幂等记录是否存在，不存在就插入
        if (idempotent == null) {
            idempotent = buildIdempotent(idempotentId);
            try {
                // 幂等的接口，在并发时，同时插入幂等记录时，只能让一个成功。（唯一约束来保证）
                idempotentDao.add(idempotent);
            } catch (DuplicateKeyException e) {
                throw IdempotentErrors.OTHER_THREAD_PROCESSING.exp();
            }
            return null;
        }

        if (STATUS_SUCCESS.equals(idempotent.getStatus())) {
            // 存在成功的幂等记录，就直接返回
            logger.info("IdempotentAspect - biz excute success last time! Return last result!");
            return lastReturn(idempotent);
        }

        // 前一次失败，这一次继续重做
        if (STATUS_FAIL.equals(idempotent.getStatus())) {
            logger.warn("IdempotentAspect - biz excute fail last time, continue executing origin origin method!");
            int count = idempotentDao.updateStatus(idempotentId,
                    STATUS_PROCESSING, STATUS_FAIL, null, null, null, null);
            if (count != 1) {
                logger.error("IdempotentAspect - update status error, update count={}", count);
                throw IdempotentErrors.OTHER_THREAD_PROCESSING.exp();
            }
            return null;
        } else if (STATUS_PROCESSING.equals(idempotent.getStatus())) {
            logger.warn("IdempotentAspect - idempotent status is {}, other thread maybe executing!", idempotent.getStatus());
            throw IdempotentErrors.OTHER_THREAD_PROCESSING.exp();
        } else {
            logger.warn("IdempotentAspect - unknown idempotent status : {}, ERROR!", idempotent.getStatus());
            throw IdempotentErrors.IDENPOTENT_ERROR.exp();
        }
    }

    /**
     * 返回上次执行的结果
     *
     * @param idempotent
     * @return
     */
    private ResultHolder lastReturn(Idempotent idempotent) {
        if(idempotent.getReturnByte() == null){
            return new ResultHolder(null);
        }

        ByteArrayInputStream is = new ByteArrayInputStream(idempotent.getReturnByte());
        Hessian2Input h2i = new Hessian2Input(is);
        h2i.setSerializerFactory(new SerializerFactory());
        try {
            Class<?> clazz = Class.forName(idempotent.getReturnClass());
            return new ResultHolder(h2i.readObject(clazz));
        } catch (Exception e) {
            throw IdempotentErrors.DESERIALIZATION_FAIL.exp(e, idempotent.getIdempotentId());
        } finally {
            try {
                is.close();
            } catch (Exception e) {
                logger.error("ByteArrayInputStream close error!", e);
            }
        }
    }


    private Idempotent buildIdempotent(String idempotentId) {
        Idempotent idempotent = new Idempotent();
        idempotent.setIdempotentId(idempotentId);
        idempotent.setStatus(STATUS_PROCESSING);
        return idempotent;
    }

    private String getIdempotentId(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        String idExpression = method.getAnnotation(com.dazong.common.idempotent.Idempotent.class).value();
        if (StringUtils.isEmpty(idExpression)) {
            throw new IllegalStateException(method.getName() + "上@Idempotent的value不能为空");
        }

        // spel
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context =
                new MethodBasedEvaluationContext(pjp.getTarget(), method, pjp.getArgs(), new DefaultParameterNameDiscoverer());
        return parser.parseExpression(idExpression).getValue(context, String.class);
    }


    static class ResultHolder {
        private Object result;

        public ResultHolder(Object result) {
            this.result = result;
        }

        public Object getResult() {
            return result;
        }
    }
}
