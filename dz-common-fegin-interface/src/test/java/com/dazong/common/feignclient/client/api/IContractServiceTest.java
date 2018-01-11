package com.dazong.common.feignclient.client.api;

import com.dazong.common.feign.client.dto.request.ProtocolRequest;
import com.dazong.common.feign.client.dto.response.ProtocolResponse;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dazong.common.feign.client.api.IContractService;
import com.dazong.common.feign.client.dto.request.AgreementRequest;
import com.dazong.common.feignclient.FeignclientApplication;
import com.dazong.common.resp.DataResponse;

/**
 * @author yanghui
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FeignclientApplication.class)
public class IContractServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(IUserInfoServiceTest.class);

    @Autowired
    private IContractService contractService;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Test
    public void testCheckIsAgreement() {
        AgreementRequest agreementRequest = new AgreementRequest();
        agreementRequest.setCompanyIds("1001");
        agreementRequest.setUrl("/taohua/sell/add_sell");
        agreementRequest.setWarehouseCode("1");
        DataResponse<?> result = this.contractService.checkIsAgreement(agreementRequest);
        System.out.println(result);
    }

    @Test
    public void testCheckOwnerIsProtocol() {
        ProtocolRequest request = new ProtocolRequest();
        request.setOwnerCode("100001188");
        request.setWarehouseCode("1");
        DataResponse<ProtocolResponse> responseDataResponse = this.contractService.checkOwnerIsProtocol(request);
        if (responseDataResponse.isSuccess()) {
            logger.info("result={}", responseDataResponse.getData());
        }
    }
}
