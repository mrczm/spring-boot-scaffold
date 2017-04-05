package com.sj.module.sys.aop;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Created by yangrd on 2017/3/27.
 */
@Component
@Aspect
public class WebLoggerAspect {

    private static final Logger logger = LoggerFactory.getLogger(WebLoggerAspect.class);

    @Pointcut("execution(* com.sj.module.*.web.api.*.*(..))")
    public void webLog() {
    }


    // 在一个方法执行之前，执行通知。
//    @Before("webLog()")
    public void doBeforeTask(JoinPoint joinPoint) {


        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        String user =  request.getHeader("User-Agent");
        logger.info("用户 => ", user);
        logger.info("请求参数 => url: {}, method: {}, uri: {}, params: {}", url, method, uri, queryString);
        logger.info("CLASS_METHOD => {}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
    }

    @Before("@annotation(requiresPermissions)")
    public void Ann(JoinPoint joinPoint, RequiresPermissions requiresPermissions){
        logger.info(Arrays.toString(requiresPermissions.value()));
        logger.info("CLASS_METHOD => {}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info(JSON.toJSONString(joinPoint.getArgs()));
    }


}
