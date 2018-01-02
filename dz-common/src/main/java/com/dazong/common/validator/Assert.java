package com.dazong.common.validator;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.mvel2.MVEL;
import org.springframework.util.StringUtils;

import com.dazong.common.exceptions.ArgumetNullException;
import com.dazong.common.exceptions.BusinessException;

public class Assert {
	public static final String OBJECT_CLASS_NAME = "java.lang.Object";

	private Assert() {
		throw new BusinessException("Cannot be instantiated!");
	}

	public static void assertNotNull(Object object) {
		recursion(object.getClass(), object);
	}

	private static void recursion(Class<?> clz, Object object) {
		if (OBJECT_CLASS_NAME.equals(clz.getName())) {
			return;
		}
		Field[] fields = clz.getDeclaredFields();
		for (Field field : fields) {
			NotNull notNull = field.getAnnotation(NotNull.class);
			if (notNull == null) {
				continue;
			}
			if (StringUtils.isEmpty(notNull.expression())) {
				fieldNotNull(field, object, notNull);
			} else {
				validateExpression(field, object, notNull);
			}
		}
		recursion(clz.getSuperclass(), object);
	}

	private static void validateExpression(Field field, Object object, NotNull notNull) {
		String expression = notNull.expression();
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("object", object);
		boolean result = MVEL.eval(expression.replace("#this", "object"), vars, Boolean.class);
		if (result) {
			fieldNotNull(field, object, notNull);
		}
	}

	private static void fieldNotNull(Field field, Object object, NotNull notNull) {
		field.setAccessible(true);
		Object value = null;
		try {
			value = field.get(object);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
		if (value == null) {
			throw new ArgumetNullException(notNull.code(), notNull.message());
		}
		field.setAccessible(false);
	}
}
