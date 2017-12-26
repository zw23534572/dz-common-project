package com.dazong.common.web.interceptor;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/**
 * 验证是否已经登录，若未登录跳转到登录页面
 */
public class AuthTokenInterceptor extends HandlerInterceptorAdapter {
   
    private static final Logger log = LoggerFactory.getLogger(AuthTokenInterceptor.class);

    private String authKey;
    
    private String authToken;
    
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws IOException,
            MalformedURLException {
        //跳过非API接口
        String uri= request.getRequestURI();
        if(!uri.startsWith("/api")){
            return true;
        }
        String paramAuthToken = request.getParameter(authKey);
        if(null == authToken){
            
            throw new IllegalStateException("服务端未配置授权码");
        }
        
        if(!authToken.equals(paramAuthToken)){
            log.error("访问未提供权限码!阻止访问!{}",uri);
            response.sendError(401, "未设置授权访问码!");;
            return false;
        }
        return true;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }
    
    
}
