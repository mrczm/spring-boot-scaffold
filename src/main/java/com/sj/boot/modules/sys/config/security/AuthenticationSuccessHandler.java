package com.sj.boot.modules.sys.config.security;

import com.alibaba.fastjson.JSON;
import com.sj.boot.common.ResultGenerator;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yangrd
 * @date 2019/1/17
 **/
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final String LOGIN_SUCCESS_RESULT = JSON.toJSONString(ResultGenerator.ok().getBody());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
        if (RequestUtils.isAjax(request)) {
            ResponseUtils.print(response, LOGIN_SUCCESS_RESULT);
        } else {
            super.onAuthenticationSuccess(request, response, auth);
        }
    }

}