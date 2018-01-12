package com.dazong.common.feign.client.api;

import com.dazong.common.feign.client.config.XmlAndFastJsonConfiguration;
import com.dazong.common.resp.DataResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author lori.li
 */
@FeignClient(name = "${feignclient.up.serviceId}",
        url = "${feignclient.up.url}",
        configuration = XmlAndFastJsonConfiguration.class)
public interface IUpService {

    /**
     * 调用PHP上传文件接口
     * 此接口需要进行数字签名，PHP未公开此接口，所以目前需要自己实现一套数字签名并把参数传给PHP
     * 使用方法见UT
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    DataResponse<String> upload(MultiValueMap<String, Object> param);
}
