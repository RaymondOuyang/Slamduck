package com.missingvia.slamduck.common.web;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.missingvia.slamduck.common.service.ServiceException;

/**
 * 控制器异常处理，对表单提交返回的CommonResponse做了特殊处理。
 * 
 */
public class MyExceptionHandlerExceptionResolver extends
		ExceptionHandlerExceptionResolver {

	private String defaultErrorView = "/commons/errors/error.jsp";
	
	private String unauthorizedErrorView = "/commons/errors/403.jsp";//没有权限的错误提示页面

	private MappingJackson2JsonView mappingJackson2JsonView;

	public String getDefaultErrorView() {
		return defaultErrorView;
	}

	public void setDefaultErrorView(String defaultErrorView) {
		this.defaultErrorView = defaultErrorView;
	}

	public MappingJackson2JsonView getMappingJackson2JsonView() {
		if (mappingJackson2JsonView == null) {
			return new MappingJackson2JsonView();
		}
		return mappingJackson2JsonView;
	}

	public void setMappingJackson2JsonView(
			MappingJackson2JsonView mappingJackson2JsonView) {
		this.mappingJackson2JsonView = mappingJackson2JsonView;
	}

	@Override
	public ModelAndView doResolveHandlerMethodException(
			HttpServletRequest request, HttpServletResponse response,
			HandlerMethod handlerMethod, Exception exception) {
		if (exception != null) {
			logger.error(exception);
		}
		if (handlerMethod == null) {
			return null;
		}
		Method method = handlerMethod.getMethod();
		if (method == null) {
			return null;
		}
		ModelAndView returnValue = super.doResolveHandlerMethodException(
				request, response, handlerMethod, exception);
		ResponseBody responseBodyAnn = AnnotationUtils.findAnnotation(method,
				ResponseBody.class);

		//没有权限的异常判断，
//		if (exception instanceof UnauthorizedException) {
//			String requestType = request.getHeader("X-Requested-With");
//			if(requestType != null && "XMLHttpRequest".equals(requestType)) {
//				CommonResponse responseInfo = new CommonResponse();
//				responseInfo.setCode(1500);
//				responseInfo.setMessage(ErrorMessage.getMessage(ErrorCode.FORBIDDEN));
//				return new ModelAndView(getMappingJackson2JsonView(),responseInfo.toMap());
//			}
//			else{
//				setDefaultErrorView(unauthorizedErrorView);//设置重定向到403页面
//				return getDefaultModelAndView(new Exception("没有权限"));
//			}
//		}
		
		// 使用注解，需要输出JSON格式的
		if (responseBodyAnn != null) {
			// 对通用响应的处理。
			CommonResponse responseInfo = new CommonResponse();
			responseInfo.setCode(1500);
			responseInfo.setMessage(exception.getMessage());
			if (exception instanceof ServiceException) {
				ServiceException se = (ServiceException) exception;
				if (se.getErrorCode() != null) {
					responseInfo.setCode(1500);
				}
			}
			return new ModelAndView(getMappingJackson2JsonView(),responseInfo.toMap());
			
		}// end if( responseBodyAnn != null ) {
		if (returnValue == null || returnValue.getViewName() == null) {
			return getDefaultModelAndView(exception);
		}
		returnValue.addObject("error", getError(exception));
		return returnValue;

	}// end doResolveHandlerMethodException

	

	private ModelAndView getDefaultModelAndView(Exception exception) {
		if (StringUtils.isBlank(getDefaultErrorView())) {
			return null;
		}
		ModelAndView mv = null;
		if (getDefaultErrorView().endsWith(".jsp")) {
			// 需要重定向的页面
			mv = new ModelAndView("redirect:" + getDefaultErrorView());
		} else {
			mv = new ModelAndView(getDefaultErrorView());
		}
		mv.addObject("error", getError(exception));
		return mv;
	}

	private Map<String, Object> getError(Exception exception) {
		Map<String, Object> error = new HashMap<String, Object>();
		error.put("message", exception.getMessage());
		return error;
	}

}
