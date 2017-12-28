package com.dazong.example.service.common.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.dazong.common.resp.DataResponse;
import com.dazong.common.web.util.HttpInvokeException;
import com.dazong.eye.api.RpcPoint;
import com.dazong.eye.api.TraceContext;
import com.dazong.example.common.constant.URLConfig;
import com.dazong.example.domain.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author huqichao
 * @create 2017-10-16 20:25
 **/
@Service
public class HttpClientServiceImpl {

    private Logger logger = LoggerFactory.getLogger(HttpClientServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private URLConfig urlConfig;

    public DataResponse<UserInfo> queryUserInfoByUserID(String userId){
        DataResponse<UserInfo> response = new DataResponse<>();
        try {
            UserInfo user = new UserInfo();
            user.setUserId(userId);
            String result = sendHttp(urlConfig.getUserCenter(), user, String.class);
            response = JSON.parseObject(result, new TypeReference<DataResponse<UserInfo>>(){});
        } catch (HttpInvokeException e) {
            logger.error("queryUserInfoByUserID", e);
        }
        return response;
    }

    private <T> T sendHttp(String url, Object requestParam, Class<T> responseType) throws HttpInvokeException {
        HttpHeaders headers = new HttpHeaders();
        RpcPoint rpcPoint = TraceContext.currentPoint();
        if (rpcPoint != null){
            headers.add("TraceId", rpcPoint.getTraceId());
        }
        String param = JSON.toJSONString(requestParam);
        logger.debug("请求PHP--------->URL: {}, 参数: {}", url, param);
        HttpEntity<?> requestEntity = new HttpEntity<>(param, headers);
        ResponseEntity<T> response = restTemplate.postForEntity(url, requestEntity, responseType);
        if(response.getStatusCode() == HttpStatus.OK){
            logger.debug("请求PHP--------->URL: {}, 返回值: {}", url, response.getBody());
            return response.getBody();
        }else {
            throw new HttpInvokeException(response.getStatusCode());
        }
    }
}
