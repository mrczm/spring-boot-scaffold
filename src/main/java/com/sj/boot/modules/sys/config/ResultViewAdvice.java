package com.sj.boot.modules.sys.config;

import com.sj.boot.common.Result;
import com.sj.boot.common.ResultGenerator;
import com.sj.boot.common.ResultView;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * @author yangrd
 * @date 2019/1/15
 **/
//@ControllerAdvice
public class ResultViewAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.getParameterType().isAnnotationPresent(ResultView.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        if (returnType.getParameterType().isAnnotationPresent(ResultView.class)) {
            Optional<RequestMapping> requestMappingOptional = Arrays.stream(Objects.requireNonNull(returnType.getMethod()).getAnnotations()).filter(annotation -> annotation.getClass().isAnnotationPresent(RequestMapping.class)).map(annotation -> annotation.getClass().getAnnotation(RequestMapping.class)).findFirst();
            RequestMapping requestMapping = requestMappingOptional.orElseGet(() -> Objects.requireNonNull(returnType.getMethod()).isAnnotationPresent(RequestMapping.class) ? returnType.getMethod().getAnnotation(RequestMapping.class) : null);
            if (Objects.nonNull(requestMapping)) {
                RequestMethod requestMethod = requestMapping.method()[0];
                if (requestMethod.equals(RequestMethod.POST)) {
                    Result<Object> created = ResultGenerator.created(body);
                    response.setStatusCode(created.getStatusCode());
                    return created.getBody();
                } else if (requestMethod.equals(RequestMethod.PUT) || requestMethod.equals(RequestMethod.DELETE) || requestMethod.equals(RequestMethod.PATCH)) {
                    Result<Object> noContentResult = ResultGenerator.noContent();
                    response.setStatusCode(noContentResult.getStatusCode());
                    return noContentResult.getBody();
                }
            } else {
                return ResultGenerator.ok(body).getBody();
            }
        } else {
            if (returnType.getParameterType().isAssignableFrom(String.class)) {
                return body;
            } else {
                Result<Object> noContentResult = ResultGenerator.noContent();
                response.setStatusCode(noContentResult.getStatusCode());
                return noContentResult.getBody();
            }
        }
        return body;
    }
}