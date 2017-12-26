package com.dazong.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.dazong.common.exceptions.BusinessException;
import com.dazong.common.resp.CommonRespStatus;
import com.dazong.common.resp.CommonResponse;

public class JsonMappingExceptionResolver extends SimpleMappingExceptionResolver {
	private static final Logger log = LoggerFactory.getLogger(JsonMappingExceptionResolver.class);

	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		log.error("请求异常！请求地址{}", request.getRequestURL(), ex);
		// Expose ModelAndView for json response
		ModelAndView jsonmv = new ModelAndView(new JsonViewer());

		if (ex instanceof BusinessException) {
			BusinessException businessException = (BusinessException) ex;
			jsonmv.addAllObjects(businessException.toMap());

		} else {
			CommonResponse resp = CommonRespStatus.FAIL.wapperResponse(ex.getMessage());
			jsonmv.addAllObjects(resp.toMapModel());
		}

		return jsonmv;
	}
}
