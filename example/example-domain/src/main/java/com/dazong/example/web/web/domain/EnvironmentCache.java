package com.dazong.example.web.web.domain;

import com.dazong.common.feign.client.dto.response.UserInfo;

/**
 * 本机缓存
 * @author huqichao
 * @create 2017-10-16 19:54
 **/
public class EnvironmentCache {

    private EnvironmentCache(){}

    private static ThreadLocal<UserInfo> userInfoThreadLocal = new ThreadLocal<>();

    public static void saveUserInfo(UserInfo user) {
        userInfoThreadLocal.set(user);
    }
    
    public static void remove(){
    	userInfoThreadLocal.remove();
    }

    public static UserInfo getUserInfo(){
        return userInfoThreadLocal.get();
    }
}
