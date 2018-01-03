package com.dazong.common;

/**
 * 
 * @author luobinwen
 *
 */
public interface IResultStatus  {
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