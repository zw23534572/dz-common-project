package com.dazong.example.service.test;

import javax.validation.Valid;
import javax.validation.constraints.Null;

import com.dazong.common.resp.DataResponse;
import com.dazong.example.domain.UserInfo;

public interface UserService {
	public DataResponse<UserInfo> getUser(@Null String userId);
	
	public DataResponse<UserInfo> getUser(@Valid UserInfo userInfo);
}
