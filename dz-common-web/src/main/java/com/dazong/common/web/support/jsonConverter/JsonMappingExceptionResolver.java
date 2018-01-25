package com.dazong.common.web.support.jsonConverter;

import com.dazong.common.CommonStatus;
import com.dazong.common.exceptions.BaseApplicationException;
import com.dazong.common.resp.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

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


        Map<String, Object> resultMap;
        if (ex instanceof BaseApplicationException) {
            resultMap = ((BaseApplicationException) ex).toMap();
        } else {
            CommonResponse resp = new CommonResponse(CommonStatus.ERROR.joinSystemStatusCode(ex.getMessage()));
            resultMap = resp.toMapModel();
        }

        logger.info("请求异常！结果：{}", resultMap);
        jsonmv.addAllObjects(resultMap);
        return jsonmv;
    }
}
