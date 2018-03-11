package com.dazong.common.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import com.dazong.common.CommonStatus;
import com.dazong.common.exceptions.BusinessException;

/**
 * hibernate-validator校验工具类
 * @author Zhouwei
 */
public class ValidatorUtils {
    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 校验对象
     * @param object        待校验对象
     * @param groups        待校验的组
     */
    public static void validateEntity(Object object, Class<?>... groups) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            StringBuilder msg = new StringBuilder();
            for(ConstraintViolation<Object> constraint:  constraintViolations){
                msg.append(constraint.getPropertyPath()).append(":");
                msg.append(constraint.getMessage()).append(";");
            }
            throw new BusinessException(CommonStatus.FAIL.joinSystemStatusCode(msg.toString()));
        }
    }
}
