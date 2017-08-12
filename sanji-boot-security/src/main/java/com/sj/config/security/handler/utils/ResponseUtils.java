package com.sj.config.security.handler.utils;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by yangrd on 2017/7/4.
 */
public class ResponseUtils {

    public static HttpServletResponse utf8AndJson(HttpServletResponse response) {
        response.setContentType("text/json;charset=utf-8");
        return response;
    }
}
