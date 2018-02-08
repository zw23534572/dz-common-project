package com.dazong.common.feign.client.api;

import com.dazong.common.feign.client.config.FastJsonConfiguration;
import com.dazong.common.feign.client.dto.request.*;
import com.dazong.common.feign.client.dto.response.ProtocolResponse;
import com.dazong.common.feign.client.dto.response.VipResponse;
import com.dazong.common.resp.DataResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * @author luobw
 */
@FeignClient(name = "${feignclient.htapi.serviceId:htapi}", url = "${feignclient.htapi.url:http://htapi.dazong.com}", configuration = FastJsonConfiguration.class)
public interface IContractService {
    /**
     * <B>方法名称：创建交易确认书</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @param createContractRequest
     * @return String
     */
    @RequestMapping(value = "/contract/add_contract", method = RequestMethod.POST)
    DataResponse<String> createContract(@RequestBody CreateContractRequest createContractRequest);

    /**
     * <B>方法名称：校验协议权限</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @param agreementRequest
     * @return String
     */
    @RequestMapping(value = "/contract/check_url_protocols", method = RequestMethod.POST)
    DataResponse<String> checkIsAgreement(@RequestBody AgreementRequest agreementRequest);

    /**
     * 验证货主与仓库是否签有协议
     *
     * @param request
     * @return ProtocolResponse
     */
    @RequestMapping(value = "/contract/get_protocol_by_company", method = RequestMethod.POST)
    DataResponse<ProtocolResponse> checkOwnerIsProtocol(@RequestBody ProtocolRequest request);

    /**
     * 查询货主是否是VIP客户
     * 注意点：返回成功的code为20271,所以不能使用isSuccess()判断是否调用成功
     *
     * @param request
     * @return Map<String               ,               VipResponse>
     */
    @RequestMapping(value = "/contract/get_fetch_by_turncode", method = RequestMethod.POST)
    DataResponse<Map<String, VipResponse>> checkIsVip(@RequestBody CompanyRequest request);

    /**
     * 发送仓单变更凭证信息给PHP
     * 注意点：返回成功的code为20271,所以不能使用isSuccess()判断是否调用成功
     *
     * @param request
     * @return String
     */
    @RequestMapping(value = "/contract/add_warehouse_switch_order", method = RequestMethod.POST)
    DataResponse<String> sendReceiptChangeCertificate(@RequestBody ReceiptChangeCertificateRequest request);
}