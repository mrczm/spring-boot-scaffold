package com.sj.module.sys.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by sunxyz on 2017/3/13.
 */
@Entity
@Table(name = "sys_group")
public class Group extends BaseEntity<Long> {

    private String name;//给用户看

    private String description;//描述

    @ManyToMany
    @JoinTable(name = "sys_group_role", joinColumns = {@JoinColumn(name = "group_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roleSet;

    @ManyToMany
    @JoinTable(name = "sys_group_menu", joinColumns = {@JoinColumn(name = "group_id")}, inverseJoinColumns = {@JoinColumn(name = "menu_id")})
    private Set<Menu> menuSet;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Role> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(Set<Role> roleSet) {
        this.roleSet = roleSet;
    }

    public Set<Menu> getMenuSet() {
        return menuSet;
    }

    public void setMenuSet(Set<Menu> menuSet) {
        this.menuSet = menuSet;
    }
}
