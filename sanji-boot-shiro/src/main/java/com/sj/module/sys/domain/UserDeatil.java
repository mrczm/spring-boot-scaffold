package com.sj.module.sys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * 添加用户详细信息 用户表尽量不要修改
 * Created by sunxyz on 2017/3/13.
 */
@JsonIgnoreProperties({"hibernateLazyInitializer", "password", "news"})
@DynamicUpdate
@Entity(name = "sys_userdeatil")
@PrimaryKeyJoinColumn(name = "user_id")
public class UserDeatil extends User {

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
