package com.sj.module.sys.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by sunxyz on 2017/3/14.
 */
@MappedSuperclass
public class BaseEntity<PK extends Serializable> extends AbstractPersistable<PK> {

    @Temporal(TemporalType.TIMESTAMP)
    private Date crateTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    private String mark;

    public Date getCrateTime() {
        return crateTime;
    }

    public void setCrateTime(Date crateTime) {
        this.crateTime = crateTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
