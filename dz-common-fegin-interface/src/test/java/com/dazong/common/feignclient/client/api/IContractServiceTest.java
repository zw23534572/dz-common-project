package com.dazong.common.feignclient.client.api;

import com.dazong.common.feign.client.api.IContractService;
import com.dazong.common.feign.client.dto.request.*;
import com.dazong.common.feign.client.dto.response.ProtocolResponse;
import com.dazong.common.feign.client.dto.response.VipResponse;
import com.dazong.common.feignclient.FeignclientApplication;
import com.dazong.common.resp.DataResponse;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    public void testCreateContract(){
        //用户ID及货主要存在于CRM中
        CreateContractRequest request = new CreateContractRequest();
        request.setOrderNo(System.currentTimeMillis());
        request.setPayType(0);
        request.setBoardNo(System.currentTimeMillis());
        request.setWhId(1);
        request.setWhName("UTest");
        request.setRealWhReceipt("buy"+System.currentTimeMillis());
        request.setBoardWhReceipt("sell"+System.currentTimeMillis());
        request.setSellType(1);
        request.setBoardMode(1);
        request.setInvoiceMode(1);
        request.setBuyDealerId(Long.valueOf(1001995));
        request.setBuyDealerName("UTest");
        request.setSellDealerId(Long.valueOf(1002558));
        request.setSellDealerName("sellUTest");
        request.setBrandId(1);
        request.setBrandName("ABA");
        request.setClassId(1);
        request.setClassName("铜");
        request.setCategoryId(1);
        request.setCategoryName("铜");
        request.setRealQuantity(new BigDecimal(25));
        request.setDealPrice(BigDecimal.valueOf(4000));
        request.setTaxPrice(BigDecimal.valueOf(42000));
        request.setTotalAmount(BigDecimal.valueOf(1000000));
        request.setPremium(BigDecimal.valueOf(20));
        request.setBuyCoId(Long.valueOf(100001188));
        request.setBuyCoName("UTestBuy");
        request.setBuyCoTel("321321321");
        request.setSellCoId(Long.valueOf(1001));
        request.setSellCoName("UTestSell");
        request.setSellCoTel("321213221");
        request.setDepositRadio("5");
        request.setPenaltyRadio("10");
        request.setTradeMode(10);
        logger.info("request:{}",request);
        DataResponse<String> response = this.contractService.createContract(request);
        if (20271==response.getCode()){
            logger.info("执行结果：{}",response);
        }
    }

    @Test
    public void testCheckIsAgreement() {
        AgreementRequest agreementRequest = new AgreementRequest();
        agreementRequest.setCompanyIds("1001");
        agreementRequest.setUrl("/taohua/sell/add_sell");
        agreementRequest.setWarehouseCode("1");
        DataResponse<String> result = this.contractService.checkIsAgreement(agreementRequest);
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
