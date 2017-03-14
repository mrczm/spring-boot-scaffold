package com.sj.module.sys.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.Set;

/**
 * Created by sunxyz on 2017/3/13.
 */
@Entity
public class Role extends AbstractPersistable<Long> {

    private String name;

    @ManyToMany
    @JoinTable(name = "role_per", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "per_id")})
    private Set<PermissionResources> permissionResourcesSet;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<PermissionResources> getPermissionResourcesSet() {
        return permissionResourcesSet;
    }

    public void setPermissionResourcesSet(Set<PermissionResources> permissionResourcesSet) {
        this.permissionResourcesSet = permissionResourcesSet;
    }
}
