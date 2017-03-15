package com.sj.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Created by sunxyz on 2017/3/14.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HttpResponse<T> {

    private static final String SUCCESS_MSG = "操作成功";
    private static final String ERROR_MSG = "操作失败";
    public static final HttpResponse<String> OK = new HttpResponse<>();
    public static final HttpResponse<String> ERROR = new HttpResponse<>();

    @Enumerated(EnumType.STRING)
    private Status status;

    private String msg;

    private T content;

    static {
        ERROR.setMsg(ERROR_MSG);
    }

    {
        status = Status.SUCCESS;
        msg = SUCCESS_MSG;
    }

    public HttpResponse() {

    }

    public HttpResponse(T content) {
        this.content = content;
    }

    public static HttpResponse<String> error() {
        return ERROR;
    }

    public static <T> HttpResponse<T> error(String msg) {
        HttpResponse<T> httpResponse = new HttpResponse<>();
        httpResponse.setStatus(Status.ERROR);
        httpResponse.setMsg(msg);
        return httpResponse;
    }

    public static HttpResponse<String> ok() {
        return OK;
    }

    public static <T> HttpResponse<T> ok(T content) {
        return new HttpResponse<T>(content);
    }

    public static <T> HttpResponse<T> ok(String msg, T content) {
        HttpResponse<T> httpResponse = new HttpResponse<T>(content);
        httpResponse.setMsg(msg);
        return httpResponse;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public static enum Status {
        SUCCESS, ERROR
    }

}
