package com.dazong.common.resp;

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