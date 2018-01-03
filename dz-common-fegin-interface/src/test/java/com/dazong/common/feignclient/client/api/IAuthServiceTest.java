package com.dazong.common.feignclient.client.api;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dazong.common.feign.client.api.IAuthService;
import com.dazong.common.feign.client.dto.request.AuthRequest;
import com.dazong.common.feign.client.dto.response.AuthResponse;
import com.dazong.common.feignclient.FeignclientApplication;
import com.dazong.common.resp.DataResponse;

/**
 *  @author yanghui
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FeignclientApplication.class)
public class IAuthServiceTest {
	
	@Autowired
	private IAuthService authService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testCheckIsAuth() {
		AuthRequest authRequest = new AuthRequest();
		authRequest.setUserId(460L);
		List<String> privList = new ArrayList<String>();
		privList.add("/taohua/taohua/sell/add_sell");
		authRequest.setPrivList(privList);
		DataResponse<AuthResponse> result = this.authService.checkIsAuth(authRequest);
		System.out.println(result);
	}

}
