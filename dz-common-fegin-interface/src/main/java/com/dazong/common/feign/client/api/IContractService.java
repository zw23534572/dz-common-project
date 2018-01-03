package com.dazong.common.feign.client.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dazong.common.feign.client.config.FastJsonConfiguration;
import com.dazong.common.feign.client.dto.request.AgreementRequest;
import com.dazong.common.feign.client.dto.request.CreateContractRequest;
import com.dazong.common.resp.DataResponse;

/**
 *  @author luobw
 */
@FeignClient(name = "${feignclient.htapi.serviceId}", url = "${feignclient.htapi.url}", configuration = FastJsonConfiguration.class)
public interface IContractService {
	/**
	 * <B>�������ƣ���������ȷ����</B><BR>
	 * <B>��Ҫ˵����</B><BR>
	 * @param createContractRequest
	 * @return
	 */
	@RequestMapping(value = "/contract/add_contract", method = RequestMethod.POST)
	DataResponse<?> createContract(@RequestBody CreateContractRequest createContractRequest);
	/**
	 * <B>�������ƣ�У��Э��Ȩ��</B><BR>
	 * <B>��Ҫ˵����</B><BR>
	 * @param agreementRequest
	 * @return
	 */
	@RequestMapping(value = "/contract/check_url_protocols", method = RequestMethod.POST)
	DataResponse<?> checkIsAgreement(@RequestBody AgreementRequest agreementRequest);
	
}