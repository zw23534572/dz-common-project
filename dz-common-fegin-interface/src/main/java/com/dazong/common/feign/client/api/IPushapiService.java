package com.dazong.common.feign.client.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dazong.common.resp.DataResponse;

/**
 *  @author yanghui
 */
@FeignClient(name="${feignclient.pushapi.serviceId}",url="${feignclient.pushapi.url}")
public interface IPushapiService {

	/**
	 * <B>方法名称：检查用户U盾是否在线</B><BR>
	 * <B>概要说明：</B><BR>
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/api/get_ukey_status/{userId}", method = RequestMethod.GET)
	DataResponse<String> isUserOnline(@PathVariable("userId") String userId);
	
	/**
	 * <B>方法名称：校验U盾信息是否合法</B><BR>
	 * <B>概要说明：</B><BR>
	 * @param userId
	 * @param uusign
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/api/verify_ukey_signature/{userId}/{uusign}", method = RequestMethod.POST)
	DataResponse<String> isLegalSign(@PathVariable("userId") String userId,
								@PathVariable("uusign")String uusign,
								@RequestBody String data);
	
}

