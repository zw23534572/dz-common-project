package com.dazong.example.service.test;

import javax.validation.Valid;
import javax.validation.constraints.Null;

import com.dazong.common.feign.client.dto.response.UserInfo;
import com.dazong.common.resp.DataResponse;


public interface UserService {
	public DataResponse<UserInfo> getUser(@Null String userId);
	
	public DataResponse<UserInfo> getUser(@Valid UserInfo userInfo);
}
