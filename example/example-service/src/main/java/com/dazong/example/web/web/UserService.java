package com.dazong.example.web.web;

import javax.validation.Valid;
import javax.validation.constraints.Null;

import com.dazong.common.feign.client.dto.response.UserInfo;
import com.dazong.common.resp.DataResponse;

/**
 * 用于演示service做验证的接口
 * @author luobinwen
 *
 */
public interface UserService {
	
	/**
	 * 通过userid获取用户信息
	 * @param userId
	 * @return
	 */
	public DataResponse<UserInfo> getUser(@Null String userId);
	
	/**
	 * 获取用户信息
	 * @param userInfo
	 * @return
	 */
	public DataResponse<UserInfo> getUser(@Valid UserInfo userInfo);

	DataResponse<UserInfo> saveUser(UserInfo userInfo);
}
