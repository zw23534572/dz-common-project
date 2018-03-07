package com.dazong.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * <B>说明： http 工具类</B><BR>
 *
 * @author ZhouWei
 * @version 1.0.0.
 * @date 2018-03-07 12-14
 */
public class HttpUtils {

    /**
     * SESSION ID KEY
     */
    public static final String SESSION_ID = "JSESSIONID";

    /**
     * 获取SESSIONID
     */
    public static String getSessionId(HttpServletRequest httpServletRequest) {
        return getCookieValue(httpServletRequest, "JSESSIONID");
    }

    /**
     * 根据key值，获取cookie对应的值
     *
     * @param httpServletRequest
     * @param key
     * @return
     */
    public static String getCookieValue(HttpServletRequest httpServletRequest, String key) {
        Cookie[] cookies = httpServletRequest.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(key)) {
                return cookie.getValue();
            }
        }
        return null;
    }

}
