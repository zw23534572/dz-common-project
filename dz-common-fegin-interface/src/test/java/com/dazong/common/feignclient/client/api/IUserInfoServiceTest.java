package com.dazong.common.feignclient.client.api;

import com.dazong.common.feign.client.api.IUserInfoService;
import com.dazong.common.feign.client.dto.request.*;
import com.dazong.common.feign.client.dto.response.BankInfoResponse;
import com.dazong.common.feign.client.dto.response.Company;
import com.dazong.common.feign.client.dto.response.UserInfo;
import com.dazong.common.feign.client.dto.response.WhiteListResponse;
import com.dazong.common.feignclient.FeignclientApplication;
import com.dazong.common.resp.DataResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FeignclientApplication.class)
public class IUserInfoServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(IUserInfoServiceTest.class);

    @Autowired
    private IUserInfoService iUserInfoService;

    @Test
    public void testQueryUserByUserId(){
        UserRequest request = new UserRequest();
        request.setUserId(Long.valueOf(1001995));
        DataResponse<UserInfo> response = this.iUserInfoService.queryUserByUserId(request);
        if (response.isSuccess()){
            logger.info("执行结果：{}",response);
        }
    }

    @Test
    public void testCheckSensitiveWords(){
        SensitiveRequest request = new SensitiveRequest();
        List<String> list = new ArrayList<>();
        list.add("8899");
        request.setKeywords(list);
        DataResponse<String> response = this.iUserInfoService.checkSensitiveWords(request);
        if (response.isSuccess()){
            logger.info("执行结果：{}",response);
        }
    }

    @Test
    public void testQueryCompanyByComId(){
        CompanyRequest request = new CompanyRequest();
        request.setCompanyId(Long.valueOf(1001));
        DataResponse<Company> response = this.iUserInfoService.queryCompanyByComId(request);
        if (response.isSuccess()){
            logger.info("执行结果：{}",response);
        }
    }

    @Test
    public void testCheckIsWhite(){
        WhiteRequest request = new WhiteRequest();
        List<Long> list = new ArrayList<>();
        list.add(Long.valueOf(1001));
        request.setCompanyIds(list);
        DataResponse<String> response = this.iUserInfoService.checkIsWhite(request);
        if (response.isSuccess()){
            logger.info("执行结果：{}",response);
        }
    }

    @Test
    public void testGetBankInfo(){
        BankInfoRequest request = new BankInfoRequest();
        request.setCompanyId(Long.valueOf(1001));
        DataResponse<List<BankInfoResponse>> response = this.iUserInfoService.getBankInfo(request);
        if (response.isSuccess()){
            logger.info("执行结果：{}",response);
        }
    }

    @Test
    public void testGetWhiteList() {
        WhiteRequest whiteRequest = new WhiteRequest();
        List<Long> list = new ArrayList<>();
        list.add(Long.valueOf(200162187));
        list.add(Long.valueOf(100001188));
        whiteRequest.setCompanyIds(list);
        DataResponse<List<WhiteListResponse>> response = this.iUserInfoService.getWhiteList(whiteRequest);
        if (response.isSuccess() && null != response.getData()) {
            for (WhiteListResponse item : response.getData()) {
                logger.info("companyID={},status={}", item.getCompanyID(), item.getStatus());
            }
        }
    }

    @Test
    public void testGetWarehouseByName() {
        WarehouseRequest request = new WarehouseRequest();
        request.setWarehouseName("飞马佛山库");
        DataResponse<String> response = this.iUserInfoService.getWarehouseByName(request);
        if (response.isSuccess()) {
            logger.info("result={}", response.getData());
        }
    }

    @Test
    public void testGetGoodsOwnerList() {
        PageRequest request = new PageRequest();
        request.setPage(1);
        request.setPageSize(10);
        DataResponse<List<Company>> response = this.iUserInfoService.getGoodsOwnerList(request);
        if (response.isSuccess() && null != response.getData()) {
            for (Company item : response.getData()) {
                logger.info("result={}", item);
            }
        }
    }

    @Test
    public void testGetGoodsOwnerDetail() {
        BankInfoRequest request = new BankInfoRequest();
        request.setCompanyId(Long.valueOf(100001188));
        DataResponse<Company> response = this.iUserInfoService.getGoodsOwnerDetail(request);
        if (response.isSuccess()) {
            logger.info("result={}", response.getData());
        }
    }

    @Test
    public void testGetGoodsOwnerNameList() {
        DataResponse<List<Company>> response = this.iUserInfoService.getGoodsOwnerNameList();
        if (response.isSuccess() && null != response.getData()) {
            for (Company item : response.getData()) {
                logger.info("result={}", item);
            }
        }
    }
}
