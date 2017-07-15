package com.sj.config;

import com.sj.common.Result;
import com.sj.common.ResultGenerator;
import com.sj.exception.SanJiException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by yangrd on 2017/7/7.
 */
@RestControllerAdvice
public class GlobalDefaultExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result<String> defaultErrorHandler(Exception e) throws Exception {
        e.printStackTrace();
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
            throw e;
        if (e instanceof SanJiException) {
            return ResultGenerator.error(e.getMessage());
        } else {
            return ResultGenerator.error("系统操作异常 异常信息:" + e.getMessage());
        }
    }
}
