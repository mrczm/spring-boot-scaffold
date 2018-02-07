package com.sj.modules.sys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;

/**
 * 添加用户详细信息 用户表尽量不要修改
 * Created by sunxyz on 2017/3/13.
 */
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "news"})
@DynamicUpdate
@Entity
@Table(name = "sys_user_details")
@PrimaryKeyJoinColumn(name = "user_id")
public class UserDetails extends User {

    private String avatar;//头像

    private String nickname;//昵称

    private String description;//描述

    @Enumerated
    private Sex sex;//性别

    private Date birthday;//生日

    private String phone;//手机

    //    @Email
    private String email;//邮箱

    public static enum Sex {
        MAN, WO_MAN
    }
}
