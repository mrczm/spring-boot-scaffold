package com.sj.config.shiro.filter;

import com.sj.common.Result;
import com.sj.config.shiro.util.ResponseUtil;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yangrd on 2017/4/28.
 * 扩展了 authc 并根据是否是 ajax请求返回不同的数据
 */
public class MyAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //判断是不是Ajax请求
        if ("XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"))) {
            //输出json串
            ResponseUtil.out((HttpServletResponse) response, Result.error("登陆过时").setStatus(Result.Status.KICKOUT));
            return false;
        } else {
            return super.onAccessDenied(request, response);
        }
    }


}
