package com.sj.module.sys.domain;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * 只针对具有权限标识的部分进行日志记录
 * 参考 类:
 *
 * @see com.sj.module.sys.web.api.MenuController
 * 日志记录：
 * @see com.sj.module.sys.aop.WebLoggerAspect;
 * Created by yangrd on 2017/4/5.
 */
@Entity(name = "sys_logger")
public class SiteLogger extends BaseEntity<Long> {

    private String userLoginName;//用户的登录名

    private String requestUri;//请求路径

    private String requestParams;//请求参数

    private String requestMethod;//请求方法类型

    private String requestUserAgent;//浏览器类型

    private String classMethod;//请求的类+方法

    private String permissions;//请求权限验证

    @Temporal(TemporalType.TIMESTAMP)
    private Date requestTime;//请求日期


    public SiteLogger() {
    }

    public SiteLogger(String userLoginName, String requestUri, String requestParams, String requestMethod, String requestUserAgent, String classMethod, String permissions, Date requestTime) {
        this.userLoginName = userLoginName;
        this.requestUri = requestUri;
        this.requestParams = requestParams;
        this.requestMethod = requestMethod;
        this.requestUserAgent = requestUserAgent;
        this.classMethod = classMethod;
        this.permissions = permissions;
        this.requestTime = requestTime;
    }

    public String getUserLoginName() {
        return userLoginName;
    }

    public SiteLogger setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName;
        return this;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public SiteLogger setRequestUri(String requestUri) {
        this.requestUri = requestUri;
        return this;
    }

    public String getRequestParams() {
        return requestParams;
    }

    public SiteLogger setRequestParams(String requestParams) {
        this.requestParams = requestParams;
        return this;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public SiteLogger setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
        return this;
    }

    public String getRequestUserAgent() {
        return requestUserAgent;
    }

    public SiteLogger setRequestUserAgent(String requestUserAgent) {
        this.requestUserAgent = requestUserAgent;
        return this;
    }

    public String getClassMethod() {
        return classMethod;
    }

    public SiteLogger setClassMethod(String classMethod) {
        this.classMethod = classMethod;
        return this;
    }

    public String getPermissions() {
        return permissions;
    }

    public SiteLogger setPermissions(String permissions) {
        this.permissions = permissions;
        return this;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public SiteLogger setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
        return this;
    }
}
