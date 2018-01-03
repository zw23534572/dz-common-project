package com.dazong.common.feign.client.api;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dazong.common.feign.client.config.FastJsonConfiguration;
import com.dazong.common.feign.client.dto.request.BankInfoRequest;
import com.dazong.common.feign.client.dto.request.CompanyRequest;
import com.dazong.common.feign.client.dto.request.SensitiveRequest;
import com.dazong.common.feign.client.dto.request.UserRequest;
import com.dazong.common.feign.client.dto.request.WhiteRequest;
import com.dazong.common.feign.client.dto.response.BankInfoResponse;
import com.dazong.common.feign.client.dto.response.Company;
import com.dazong.common.feign.client.dto.response.UserInfo;
import com.dazong.common.resp.DataResponse;

/**
 *  @author yanghui
 */
@FeignClient(name="${feignclient.userapi.serviceId}",
			 url="${feignclient.userapi.url}",
			 configuration=FastJsonConfiguration.class)
public interface IUserInfoService {
	
	/**
	 * <B>�������ƣ���ȡ�û�</B><BR>
	 * <B>��Ҫ˵����</B><BR>
	 * @param userRequest
	 * @return
	 */
	@RequestMapping(value = "/user/get_user_info", method = RequestMethod.POST)
	public DataResponse<UserInfo> queryUserByUserId(@RequestBody UserRequest userRequest);

	/**
	 * <B>�������ƣ�������дʻ�</B><BR>
	 * <B>��Ҫ˵����</B><BR>
	 * @param sensitiveRequest
	 * @return
	 */
	@RequestMapping(value = "/flex/getFlexWord", method = RequestMethod.POST)
	public DataResponse<?> checkSensitiveWords(@RequestBody SensitiveRequest sensitiveRequest);
	/**
	 * <B>�������ƣ���ȡ��˾</B><BR>
	 * <B>��Ҫ˵����</B><BR>
	 * @param companyRequest
	 * @return
	 */
	@RequestMapping(value = "/userapi/getCompanyDetail", method = RequestMethod.POST)
	public DataResponse<Company> queryCompanyByComId(@RequestBody CompanyRequest companyRequest);
	
	/**
	 * <B>�������ƣ���������</B><BR>
	 * <B>��Ҫ˵����</B><BR>
	 * @param whiteRequest
	 * @return
	 */
	@RequestMapping(value = "/flex/checkIsWhite", method = RequestMethod.POST)
	public DataResponse<?> checkIsWhite(@RequestBody WhiteRequest whiteRequest);
	
	/**
	 * <B>�������ƣ���ȡ���п���Ϣ</B><BR>
	 * <B>��Ҫ˵����</B><BR>
	 * @param bankInfoRequest
	 * @return
	 */
	@RequestMapping(value = "/pc_api/getcbank", method = RequestMethod.POST)
	public DataResponse<List<BankInfoResponse>> getBankInfo(BankInfoRequest bankInfoRequest);
}