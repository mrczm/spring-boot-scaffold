package com.sj.config.security.handler;


import com.alibaba.fastjson.JSON;
import com.sj.common.Result;
import com.sj.common.ResultGenerator;
import com.sj.config.security.handler.utils.RequestUtils;
import com.sj.config.security.handler.utils.ResponseUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yangrd on 2017/7/4.
 */
public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private static final String LOGIN_ERROR_RESULT = JSON.toJSONString(ResultGenerator.error("登录失败,请检验密码"));

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        if (RequestUtils.isAjax(request)) {
            ResponseUtils.utf8AndJson(response).getWriter().print(LOGIN_ERROR_RESULT);
            response.getWriter().flush();
        } else {
            super.onAuthenticationFailure(request, response, e);
        }
    }
}
