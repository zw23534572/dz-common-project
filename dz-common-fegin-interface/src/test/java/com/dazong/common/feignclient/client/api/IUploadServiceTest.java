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
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FeignclientApplication.class)
public class IUploadServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(IUploadServiceTest.class);

    @Autowired
    private IUploadService upService;

    @Test
    public void testUpload() {
        File file = this.createFile("testUpload");
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
        this.delFile(file);
        if (response.isSuccess()) {
            logger.info("result={}", response);
        }
    }

    private String createSign(String time, String configName) {
        return DigestUtils.md5Hex("config_name" + "time" + configName + time + "fmdz.2015");
    }

    @Test
    public void testUploadUtils1() {
        File file = this.createFile("testUploadUtils1");
        DataResponse<String> response = UploadUtils.upload(file);
        this.delFile(file);
        logger.info("result={}", response);
    }

    @Test
    public void testUploadUtils2() throws Exception {
        File file = this.createFile("testUploadUtils2");
        FileInputStream inputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("tempMultipartFile.txt", inputStream);
        DataResponse<String> response = UploadUtils.upload(multipartFile);
        this.delFile(file);
        logger.info("result={}", response);
    }

    private File createFile(String fileName) {
        String context = "Lori test1";
        String base64 = Base64Utils.encodeToString(context.getBytes());
        File file = new File("/tmp/" + fileName + ".txt");
        ByteArrayInputStream in = null;
        try (FileOutputStream out = new FileOutputStream(file)) {
            if (!file.exists()) {
                file.createNewFile();
            }
            byte[] bytes = Base64Utils.decodeFromString(base64);
            in = new ByteArrayInputStream(bytes);
            byte[] buffer = new byte[1024];
            int byteread = 0;
            while ((byteread = in.read(buffer)) != -1) {
                out.write(buffer, 0, byteread); // 文件写操作
            }
        } catch (Exception e) {
            logger.error("创建文件失败", e);
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (Exception e) {
                    logger.error("关闭输入流异常={}", e);
                }
            }
        }
        return file;
    }

    private void delFile(File file) {
        file.deleteOnExit();
    }
}
