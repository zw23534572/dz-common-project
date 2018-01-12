package com.dazong.common.feignclient.client.api;

import com.dazong.common.feign.client.api.IUploadService;
import com.dazong.common.feign.client.utils.UploadUtils;
import com.dazong.common.feignclient.FeignclientApplication;
import com.dazong.common.resp.DataResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FeignclientApplication.class)
public class IUploadServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(IUploadServiceTest.class);

    @Autowired
    private IUploadService upService;

//    @Test
    public void testUpload() {
        File file = new File("C:\\Users\\lori.li\\Desktop\\123.jpg");
        String time = String.valueOf(System.currentTimeMillis());
        String configName = "qualitybook";
        //生成数字签名
        String sign = this.createSign(time, configName);
        FileSystemResource resource = new FileSystemResource(file);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("file", resource);
        param.add("time", time);
        param.add("config_name", configName);
        param.add("sign", sign);
        DataResponse<String> response = this.upService.upload(param);
        if (response.isSuccess()) {
            logger.info("result={}", response);
        }
    }

    private String createSign(String time, String configName) {
        return DigestUtils.md5Hex("config_name" + "time" + configName + time + "fmdz.2015");
    }

//    @Test
    public void testUploadUtils1(){
        File file = new File("C:\\Users\\lori.li\\Desktop\\123.jpg");
        DataResponse<String> response = UploadUtils.upload(file);
        logger.info("result={}", response);
    }
}
