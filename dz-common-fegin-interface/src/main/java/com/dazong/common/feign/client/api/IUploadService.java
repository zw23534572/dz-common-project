package com.dazong.common.feign.client.api;

import com.dazong.common.feign.client.config.FastJsonConfiguration;
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
        configuration = FastJsonConfiguration.class)
public interface IUploadService {

    /**
     * 调用PHP上传文件接口
     * 此接口需要进行数字签名，PHP未公开此接口，所以目前需要自己实现一套数字签名并把参数传给PHP
     * 使用方法见UT
     * 建议PHP提供数字签名的接口；
     * 返回的文件地址缺少域名，域名为：https://i.dazong.com，使用者自己组装
     * 建议PHP直接返回完整的文件地址
     * <p>
     * 请注意：现提供了上传工具类UploadUtils，不用关心数字签名及域名,
     *
     * @param param
     * @return DataResponse<String>
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    DataResponse<String> upload(MultiValueMap<String, Object> param);
}
