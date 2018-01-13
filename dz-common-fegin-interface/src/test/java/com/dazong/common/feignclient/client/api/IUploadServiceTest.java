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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FeignclientApplication.class)
public class IUploadServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(IUploadServiceTest.class);

    @Autowired
    private IUploadService upService;

    //        @Test
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

    //        @Test
    public void testUploadUtils1() {
        File file = new File("C:\\Users\\lori.li\\Desktop\\123.jpg");
        DataResponse<String> response = UploadUtils.upload(file);
        logger.info("result={}", response);
    }

    //    @Test
    public void testUploadUtils2() throws Exception {
        File file = new File("C:\\Users\\lori.li\\Desktop\\123.jpg");
        FileInputStream inputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("temp.jpg", inputStream);
        DataResponse<String> response = UploadUtils.upload(multipartFile);
        logger.info("result={}", response);
    }

    @Test
    public void test() {
        //防止打包报错；上面的test都注释掉了，因为在服务器上文件不存在
    }
}
