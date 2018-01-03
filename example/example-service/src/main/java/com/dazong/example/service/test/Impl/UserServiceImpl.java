package com.dazong.example.service.test.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dazong.common.feign.client.dto.response.UserInfo;
import com.dazong.common.resp.DataResponse;
import com.dazong.example.service.common.impl.HttpClientServiceImpl;
import com.dazong.example.service.test.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private HttpClientServiceImpl httpClientServiceImpl;
	
	@Override
	public DataResponse<UserInfo> getUser(String userId) {
		return httpClientServiceImpl.queryUserInfoByUserID(userId);
	}

	@Override
	public DataResponse<UserInfo> getUser(UserInfo userInfo) {
		return httpClientServiceImpl.queryUserInfoByUserID(userInfo.getUserId().toString());
	}

}
