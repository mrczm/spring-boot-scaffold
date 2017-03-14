package com.sj.module.sys.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * //前台菜单 最好做缓存处理
 * Created by sunxyz on 2017/3/14.
 */
@Entity(name = "sys_menu")
public class Menu extends BaseEntity<Long> {

    private String name;//名称

    private String description;//描述

    private String url;//地址

    private String icon;//图标

    private Long sort;//排序

    private Long level;//层级=父级+1

    @ManyToOne
    private Menu parent;

    public Menu() {
    }

    public Menu(String name, String description, String url, String icon, Long sort, Long level, Menu parent) {
        this.name = name;
        this.description = description;
        this.url = url;
        this.icon = icon;
        this.sort = sort;
        this.level = level;
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

    public Menu getParent() {
        return parent;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }
}
