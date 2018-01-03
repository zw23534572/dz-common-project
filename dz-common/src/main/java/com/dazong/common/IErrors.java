package com.dazong.common;

import com.dazong.common.exceptions.BaseApplicationException;

/**
 * 错误枚举的接口类。统一异常转化与抛出的格式
 * 
 * @author wzy
 */
public interface IErrors<T> extends IResultStatus {

    /**
     * 采用枚举中定义的message作为返回信息
     * 
     * @return
     */
    public T parse();

    /**
     * 采用枚举中定义的message作为返回信息，并传递一些参数
     * 
     * @param args
     *            具体参数列表
     * @return
     */
    public T parse(Object... args);

    /**
     * 采用枚举中定义的code，使用自定义的message作为返回信息，并可能会带上一些参数
     * 
     * @param message
     *            错误信息
     * @param args
     *            具体参数列表
     * @return
     */
    public T parseMsg(String message, Object... args);

    /**
     * 采用枚举中定义的message作为异常的信息
     * 
     * @return
     */
    public BaseApplicationException exp();

    /**
     * 采用枚举中定义的message作为异常信息，并传递一些参数
     * 
     * @param args
     *            参数列表
     * @return
     */
    public BaseApplicationException exp(Object... args);

    /**
     * 采用枚举中定义的message作为异常信息，并传递一些参数，支持传入底层的异常
     * 
     * @param cause
     *            原始异常
     * @param args
     *            参数列表
     * @return
     */
    public BaseApplicationException exp(Throwable cause, Object... args);

    /**
     * 采用枚举中定义的code，使用自定义的message作为异常信息，并可能会带上一些参数
     * 
     * @param message
     *            错误信息
     * @param args
     *            具体参数列表
     * @return
     */
    public BaseApplicationException expMsg(String message, Object... args);

    /**
     * 采用枚举中定义的code，使用自定义的message作为异常信息，并可能会带上一些参数，并自持传入底层的异常
     * 
     * @param message
     *            错误信息
     * @param cause
     *            原始异常
     * @param args
     *            具体参数列表
     * @return
     */
    public BaseApplicationException expMsg(String message, Throwable cause, Object... args);
    
    /**
     * 获取枚举中定义的异常码
     * @return
     */
    public int getCode();

    /**
     * 获取枚举中定义的异常信息
     * @return
     */
	public String getMessage();
}
