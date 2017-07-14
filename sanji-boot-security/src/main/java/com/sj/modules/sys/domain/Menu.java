package com.sj.modules.sys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sj.common.base.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * //前台菜单 最好做缓存处理
 * Created by sunxyz on 2017/3/14.
 */
@JsonIgnoreProperties({"new", "parent"})
@Entity
@Table(name = "sys_menu")
public class Menu extends BaseEntity<Long> {

    private String name;

    @ManyToOne
    private Menu parent;
}
