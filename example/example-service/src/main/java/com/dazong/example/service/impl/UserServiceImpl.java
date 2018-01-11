package com.dazong.example.service.impl;

import com.dazong.common.idempotent.Idempotent;
import com.dazong.example.service.UserService;
import com.dazong.example.service.common.impl.HttpClientServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dazong.common.feign.client.dto.response.UserInfo;
import com.dazong.common.resp.DataResponse;

/**
 * 
 * @author luobinwen
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired(required = false)
	private HttpClientServiceImpl httpClientServiceImpl;
	
	@Override
	public DataResponse<UserInfo> getUser(String userId) {
		return httpClientServiceImpl.queryUserInfoByUserID(userId);
	}

	@Override
	public DataResponse<UserInfo> getUser(UserInfo userInfo) {
		return httpClientServiceImpl.queryUserInfoByUserID(userInfo.getUserId().toString());
	}

	/**
	 * 幂等示例
	 * @param userInfo
	 * @return
	 */
	@Override
	@Idempotent("#userInfo.userId")
	public DataResponse<UserInfo> saveUser(UserInfo userInfo) {
		System.out.println("保存userInfo = [" + userInfo + "]");
		return new DataResponse<>(userInfo);
	}

}
