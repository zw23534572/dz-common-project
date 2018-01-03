package com.dazong.common.exceptions;

import java.text.MessageFormat;

import org.apache.commons.lang3.ArrayUtils;

import com.dazong.common.IErrors;
import com.dazong.common.resp.CommonResponse;

/**
 * 返回对象和异常的组装器
 * 
 * @author wzy
 * @date 2017年8月11日 下午1:37:57
 */
public class ResponseAndExceptionBuilder {

	private ResponseAndExceptionBuilder() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * 组装返回对象
	 * 
	 * @param errorEnum
	 * @param message
	 * @param args
	 * @return
	 */
	public static CommonResponse parseMsg(Enum<? extends IErrors<?>> errorEnum, String message, Object... args) {
		IErrors<?> errorEnumItem = (IErrors<?>) errorEnum;
		CommonResponse resp = new CommonResponse();
		resp.setCode(errorEnumItem.getCode());
		String msg = message != null ? message : errorEnumItem.getMessage();
		if (ArrayUtils.isNotEmpty(args) && msg != null) {
			transformArgs(args);
			msg = MessageFormat.format(msg, args);
		}
		resp.setMsg(msg);
		return resp;
	}

	/**
	 * 组装异常对象
	 * 
	 * @param errorEnum
	 * @param message
	 * @param cause
	 * @param args
	 * @return
	 */
	public static BaseApplicationException expMsg(Enum<?> errorEnum, String message, Throwable cause, Object... args) {
		IErrors<?> errorEnumItem = (IErrors<?>) errorEnum;
		String formatedMsg = message != null ? message : errorEnumItem.getMessage();
		if (ArrayUtils.isNotEmpty(args) && formatedMsg != null) {
			transformArgs(args);
			// 将 message 中的占位符替换
			formatedMsg = MessageFormat.format(formatedMsg, args);
		}
		return new BusinessException(errorEnumItem.getCode(), formatedMsg, cause, args);
	}

	/**
	 * 对 Number 类型的参数，直接toString()。fix: MessageFormat会对数字使用NumberFormat进行转化
	 * 
	 * @param args
	 */
	private static void transformArgs(Object[] args) {
		if (args == null) {
			return;
		}
		for (int i = 0; i < args.length; i++) {
			if (args[i] != null && args[i] instanceof Number) {
				args[i] = args[i].toString();
			}
		}
	}

}
