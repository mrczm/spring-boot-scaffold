package com.sj.boot.modules.sys.config.security;

import com.alibaba.fastjson.JSON;
import com.sj.boot.common.ResultGenerator;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yangrd
 * @date 2019/1/17
 **/
public class AuthenticationFailureHandler  extends SimpleUrlAuthenticationFailureHandler {

    private static final String LOGIN_ERROR_RESULT = JSON.toJSONString(ResultGenerator.fail("登录失败,请检验密码").getBody());

    private static final String LOGIN_FROZEN_RESULT = JSON.toJSONString(ResultGenerator.fail("用户已被冻结").getBody());

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        if (RequestUtils.isAjax(request)) {
            String result = e instanceof DisabledException ? LOGIN_FROZEN_RESULT : LOGIN_ERROR_RESULT;
            ResponseUtils.print(response, result);
        } else {
            super.onAuthenticationFailure(request, response, e);
        }
    }
}
