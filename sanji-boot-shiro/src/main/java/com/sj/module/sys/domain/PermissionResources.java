package com.sj.module.sys.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

/**
 * Created by sunxyz on 2017/3/13.
 */
@Entity(name = "sys_per")
public class PermissionResources extends AbstractPersistable<Long> {

    private String name;//给用户看

    private String operation; // permession许可的操作，例如views，del等等

    @OneToOne(cascade = {CascadeType.ALL})
    private Menu menu;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
