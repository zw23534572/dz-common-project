package com.dazong.common.feign.client.utils;

import com.dazong.common.CommonStatus;
import com.dazong.common.exceptions.BusinessException;
import com.dazong.common.feign.client.api.IUploadService;
import com.dazong.common.resp.DataResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author lori.li
 */
public class UploadUtils implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(UploadUtils.class);

    private static final String CONFIG_NAME_VALUE = "qualitybook";

    private static final String CONFIG_NAME_KEY = "config_name";

    private static final String TIME = "time";

    private static final String SALT = "fmdz.2015";

    private static ApplicationContext context;

    public static DataResponse<String> upload(File file) {
        IUploadService uploadService = context.getBean(IUploadService.class);
        String time = String.valueOf(System.currentTimeMillis());
        //生成数字签名
        String sign = createSign(time, CONFIG_NAME_VALUE);
        MultiValueMap<String, Object> map = createMultiMap(file, time, CONFIG_NAME_VALUE, sign);
        DataResponse<String> response = uploadService.upload(map);
        response.setMsg("https://i.dazong.com" + response.getMsg());
        return response;
    }

    public static DataResponse<String> upload(MultipartFile multipartFile) {
        return upload(getFileByMultipartFile(multipartFile));
    }

    private static String createSign(String time, String configName) {
        return DigestUtils.md5Hex(CONFIG_NAME_KEY + TIME + configName + time + SALT);
    }

    private static MultiValueMap<String, Object> createMultiMap(File file, String time, String configNameValue, String sign) {
        if (null == file) {
            throw new BusinessException(CommonStatus.ILLEGAL_PARAM);
        }
        FileSystemResource resource = new FileSystemResource(file);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("file", resource);
        param.add(TIME, time);
        param.add(CONFIG_NAME_KEY, configNameValue);
        param.add("sign", sign);
        return param;
    }

    private static File getFileByMultipartFile(MultipartFile multipartFile) {
        String fullFileName = multipartFile.getOriginalFilename();
        if (StringUtils.isBlank(fullFileName)) {
            fullFileName = multipartFile.getName();
        }
        int point = fullFileName.lastIndexOf('.');
        String fileExtName = fullFileName.substring(point + 1, fullFileName.length());
        String fileName = fullFileName.substring(0, point) + System.currentTimeMillis();
        File f = null;
        try {
            f = File.createTempFile(fileName, "." + fileExtName);
            multipartFile.transferTo(f);
            return f;
        } catch (Exception e) {
            logger.error("转换文件异常={}", e);
            throw new BusinessException(CommonStatus.FAIL.getCode(), "文件转换失败");
        } finally {
            if (null != f) {
                f.deleteOnExit();
            }
        }
    }

    @Override
    public synchronized void setApplicationContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }
}
