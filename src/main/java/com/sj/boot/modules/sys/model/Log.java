package com.sj.boot.modules.sys.model;

import com.sj.boot.common.ResultView;
import com.sj.boot.common.spring.data.AbstractEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;

/**
 * @author yangrd
 * @date 2019/1/9
 **/
@ResultView("日志")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Log extends AbstractEntity<Log> {

    private String title;

    private String reqUri;

    private String reqMethod;

    private String controller;

    private String methodName;

    private String username;

    private String addressIp;

    private String driverName;

    private String browserName;

    private String exceptionInfo;

    private Long executeUseMillisecond;
}