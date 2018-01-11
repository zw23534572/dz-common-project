package com.dazong.common.feignclient.client.api;

import com.dazong.common.feign.client.dto.request.*;
import com.dazong.common.feign.client.dto.response.ProtocolResponse;
import com.dazong.common.feign.client.dto.response.VipResponse;
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
import com.dazong.common.feignclient.FeignclientApplication;
import com.dazong.common.resp.DataResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Test
    public void testCheckIsVip() {
        CompanyRequest request = new CompanyRequest();
        request.setCompanyId(Long.valueOf(100001188));
        request.setFetchNos("2020000060511");
        DataResponse<Map<String, VipResponse>> response = this.contractService.checkIsVip(request);
        if (20271 == response.getCode() && null != response.getData()) {
            logger.info("result={}", response.getData());
        }
    }

    @Test
    public void testSendReceiptChangeCertificate() {
        List<ReceiptChangeCertificateDetail> list = new ArrayList<>();
        ReceiptChangeCertificateDetail detail1 = new ReceiptChangeCertificateDetail();
        detail1.setChangeNum(100);
        detail1.setChangeWeight(BigDecimal.valueOf(90.8));
        detail1.setProductName("铜啊");
        detail1.setSku("CU123");
        detail1.setSourceReceiptNo(String.valueOf(System.currentTimeMillis()));
        detail1.setTargetReceiptNo(String.valueOf(System.currentTimeMillis()));
        list.add(detail1);
        ReceiptChangeCertificateDetail detail2 = new ReceiptChangeCertificateDetail();
        detail2.setChangeNum(100);
        detail2.setChangeWeight(BigDecimal.valueOf(90.8));
        detail2.setProductName("铜啊");
        detail2.setSku("CU123");
        detail2.setSourceReceiptNo(String.valueOf(System.currentTimeMillis()));
        detail2.setTargetReceiptNo(String.valueOf(System.currentTimeMillis()));
        list.add(detail2);
        ReceiptChangeCertificateRequest request = new ReceiptChangeCertificateRequest();
        request.setList(list);
        request.setSerialNumber(String.valueOf(System.currentTimeMillis()));
        request.setRefNo(String.valueOf(System.currentTimeMillis()));
        request.setBusType(60);
        request.setBusName("转权");
        request.setOwnerCode("100001188");
        request.setOwnerName("平安测试六零零零零九零零五五三一");
        request.setWarehouseCode("1");
        request.setWarehouseName("飞马合作仓3.0");
        request.setRemark("我很吊吗");
        DataResponse<String> response = this.contractService.sendReceiptChangeCertificate(request);
        if (20271 == response.getCode()) {
            logger.info("result={}", response.getData());
        }
    }
}
