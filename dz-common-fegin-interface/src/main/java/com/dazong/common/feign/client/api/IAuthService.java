package com.dazong.common.feign.client.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dazong.common.feign.client.config.FastJsonConfiguration;
import com.dazong.common.feign.client.dto.request.AuthRequest;
import com.dazong.common.feign.client.dto.response.AuthResponse;
import com.dazong.common.resp.DataResponse;

/**
 *  @author yanghui
 */
@FeignClient(name = "${feignclient.auth.serviceId}", url = "${feignclient.auth.url}", configuration = FastJsonConfiguration.class)
public interface IAuthService {
	
	/**
	 * <B>�������ƣ����urlȨ��</B><BR>
	 * <B>��Ҫ˵����</B><BR>
	 * @param authRequest
	 * @return
	 */
	@RequestMapping(value = "/api/permission/checkPriv", method = RequestMethod.POST)
	public DataResponse<AuthResponse> checkIsAuth(@RequestBody AuthRequest authRequest);

}
