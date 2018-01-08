package com.dazong.example.web.web.service.common.impl;

import com.dazong.common.feign.client.api.IUserInfoService;
import com.dazong.common.feign.client.dto.request.UserRequest;
import com.dazong.common.feign.client.dto.response.UserInfo;
import com.dazong.common.resp.DataResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

/**
 * @author huqichao
 * @create 2017-10-16 20:25
 **/
@Service
public class HttpClientServiceImpl {

	private Logger logger = LoggerFactory.getLogger(HttpClientServiceImpl.class);

	@Autowired
	private IUserInfoService userInfoService;

	public DataResponse<UserInfo> queryUserInfoByUserID(String userId) {
		UserRequest userRequest = new UserRequest();
		userRequest.setUserId(Long.parseLong(userId));
		DataResponse<UserInfo> response = userInfoService.queryUserByUserId(userRequest);

		logger.info("信息{0}", response);
		return response;
	}

}
