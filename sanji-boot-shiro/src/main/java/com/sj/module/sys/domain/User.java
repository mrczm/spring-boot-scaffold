package com.sj.module.sys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by sunxyz on 2017/3/13.
 */
@JsonIgnoreProperties({"hibernateLazyInitializer", "password", "news", "roleSet"})
@DynamicUpdate
@Entity
@Table(name = "sys_user")
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends BaseEntity<Long> {

    @NotEmpty(message = "用户名不能为空")
    @Column(unique = true, updatable = false)
    private String loginName;

    private String nickname;//昵称

    @NotEmpty(message = "密码不能为空")
    @Column(nullable = false)
    private String password;

    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinTable(name = "sys_user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roleSet;

    @ManyToOne
    private Group group;

    public String getLoginName() {
        return loginName;
    }

    public User setLoginName(String loginName) {
        this.loginName = loginName;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public User setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public Set<Role> getRoleSet() {
        return roleSet;
    }

    public User setRoleSet(Set<Role> roleSet) {
        this.roleSet = roleSet;
        return this;
    }

    public Group getGroup() {
        return group;
    }

    public User setGroup(Group group) {
        this.group = group;
        return this;
    }
}
