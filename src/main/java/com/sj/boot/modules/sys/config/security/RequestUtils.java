package com.sj.boot.modules.sys.config.security;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yangrd
 * @date 2019/1/17
 **/
public class RequestUtils {
    public static boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
    }
}
