package com.dazong.common.feignclient.client.api;

import com.dazong.common.feign.client.api.IAuthService;
import com.dazong.common.feign.client.dto.request.AuthRequest;
import com.dazong.common.feign.client.dto.response.AuthResponse;
import com.dazong.common.feignclient.FeignclientApplication;
import com.dazong.common.resp.DataResponse;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

/**
 *  @author yanghui
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FeignclientApplication.class)
public class IAuthServiceTest {
	
	static MockWebServer server = new MockWebServer();
	
	@Autowired
	private IAuthService authService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		server.start(8888);
		
		server.enqueue(new MockResponse().setHeader("content-type", "application/json").setBody("{\"code\":140200,\"data\":{\"dataList\":{\"/taohua/taohua/sell/add_sell\":false}},\"msg\":\"请求成功\",\"success\":true}"));
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		server.close();
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
		assertThat(result).isNotNull();
	}

}
