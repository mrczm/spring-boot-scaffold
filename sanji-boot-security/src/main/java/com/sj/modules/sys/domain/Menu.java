package com.sj.modules.sys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sj.common.base.domain.BaseEntity;

import javax.persistence.*;
import java.util.List;

/**
 * //前台菜单 最好做缓存处理
 * Created by sunxyz on 2017/3/14.
 */
@JsonIgnoreProperties({"new", "parent"})
@Entity
@Table(name = "sys_menu")
public class Menu extends BaseEntity<Long> {

    private String name;

    private String icon;

    private String description;

    private String url;

    private Long sort;

    @OneToMany
    private List<Menu> subMenus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public List<Menu> getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(List<Menu> subMenus) {
        this.subMenus = subMenus;
    }
}
