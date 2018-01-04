package com.dazong.common.feignclient.client.api;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dazong.common.feign.client.api.IContractService;
import com.dazong.common.feign.client.dto.request.AgreementRequest;
import com.dazong.common.feignclient.FeignclientApplication;
import com.dazong.common.resp.DataResponse;

/**
 *  @author yanghui
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FeignclientApplication.class)
public class IContractServiceTest {
	
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
}
