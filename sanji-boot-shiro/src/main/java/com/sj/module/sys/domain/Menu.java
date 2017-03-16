package com.sj.module.sys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * //前台菜单 最好做缓存处理
 * Created by sunxyz on 2017/3/14.
 */
@JsonIgnoreProperties({"new", "level", "parent"})
@Entity(name = "sys_menu")
public class Menu extends BaseEntity<Long> {

    @ManyToOne
    private Menu parent;

    private String name;//名称

    private String description;//描述

    private String url;//地址

    private String icon;//图标

    private Long sort;//排序

    private Long level;//层级=父级+1

    private String permission; // 权限标识，例如views，del等等

    @Transient
    private Long parentId;

    {
        sort = 0L;
    }

    public Menu getParent() {
        return parent;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Long getParentId() {
        if (null != parent) {
            this.parentId = parent.getId();
        }
        return parentId;
    }
}
