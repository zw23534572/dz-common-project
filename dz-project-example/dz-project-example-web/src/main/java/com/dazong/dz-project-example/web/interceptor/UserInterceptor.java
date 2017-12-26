package com.dazong.dz-project-example.web.interceptor;

import com.alibaba.fastjson.JSON;
import com.dazong.common.resp.DataResponse;
import com.dazong.dz-project-example.common.constant.ResultEnum;
import com.dazong.dz-project-example.domain.EnvironmentCache;
import com.dazong.dz-project-example.domain.UserInfo;
import com.dazong.dz-project-example.service.common.impl.HttpClientServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author huqichao
 * @create 2017-10-16 20:17
 **/
public class UserInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(UserInterceptor.class);

    @Autowired
    private HttpClientServiceImpl httpClientService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String userId = request.getHeader("userId");
        logger.info("login user check={}, {}", request.getRequestURI(), userId);
        constructLoginUser(userId);
        /**
         * 现在的业务逻辑无法做登录用户的检验，需要把业务拆分
         * 前期可以在各个业务里判断登录用户
         */
        if (EnvironmentCache.getUserInfo() == null) {
            logger.info("{},User {} is not exist.", request.getRequestURI(),userId);
            DataResponse<?> wrapper = new DataResponse<>(ResultEnum.USER_IS_NOT_EXIST.getCode(), ResultEnum.USER_IS_NOT_EXIST.getMsg());
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            response.getWriter().write(JSON.toJSONString(wrapper));
            return false;
        }
        return true;
    }

    private void constructLoginUser(String userId){
        if (!StringUtils.isEmpty(userId)) {
            DataResponse<UserInfo> response = httpClientService.queryUserInfoByUserID(userId);
            if (response != null && response.getCode() == 20200) {
                EnvironmentCache.saveUserInfo(response.getData());
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
