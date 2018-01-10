package com.dazong.common.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dazong.common.exceptions.ArgumetException;
import com.dazong.common.exceptions.PlatformException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.dazong.common.CommonStatus;
import com.dazong.common.exceptions.BusinessException;
import com.dazong.common.resp.CommonResponse;

/**
 * @author luobinwen
 */
public class JsonMappingExceptionResolver extends SimpleMappingExceptionResolver {
    private static final Logger logger = LoggerFactory.getLogger(JsonMappingExceptionResolver.class);

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                              Exception ex) {
        logger.error("请求异常！请求地址{}", request.getRequestURL(), ex);
        // Expose ModelAndView for json response
        ModelAndView jsonmv = new ModelAndView(new JsonViewer());

        if (ex instanceof BusinessException) {
            BusinessException businessException = (BusinessException) ex;
            jsonmv.addAllObjects(businessException.toMap());
        } else if (ex instanceof ArgumetException) {
            ArgumetException argumetException = (ArgumetException) ex;
            jsonmv.addAllObjects(argumetException.toMap());
        } else if (ex instanceof PlatformException) {
            PlatformException platformException = (PlatformException) ex;
            jsonmv.addAllObjects(platformException.toMap());
        } else {
            CommonResponse resp = new CommonResponse(CommonStatus.ERROR.joinSystemStatusCode(ex.getMessage()));
            jsonmv.addAllObjects(resp.toMapModel());
        }

        return jsonmv;
    }
}
