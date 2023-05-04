package org.example.controllers.api;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.ModelFactory;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Map;

@Component
public class HandlerFilterResolver implements HandlerMethodArgumentResolver {
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
//		return parameter.getParameterAnnotation(Filtered.class) != null;
		return Arrays.asList(
				parameter.getParameterType().getGenericInterfaces())
				.contains(Filter.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		Map<String, String[]> parameterMap = webRequest.getParameterMap();
		String name = ModelFactory.getNameForParameter(parameter);
		return null;
	}
}
