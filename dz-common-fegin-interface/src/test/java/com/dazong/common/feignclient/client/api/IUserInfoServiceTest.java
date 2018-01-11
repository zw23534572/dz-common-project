package com.dazong.common.feignclient.client.api;

import com.dazong.common.feign.client.api.IUserInfoService;
import com.dazong.common.feign.client.dto.request.WarehouseRequest;
import com.dazong.common.feign.client.dto.request.WhiteRequest;
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
    public void testGetWhiteList() {
        WhiteRequest whiteRequest = new WhiteRequest();
        List<Long> list = new ArrayList<>();
        list.add(Long.valueOf(200162187));
        list.add(Long.valueOf(100001188));
        whiteRequest.setCompanyIds(list);
        DataResponse<List<WhiteListResponse>> response = this.iUserInfoService.getWhiteList(whiteRequest);
        if (response.isSuccess()) {
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
}
