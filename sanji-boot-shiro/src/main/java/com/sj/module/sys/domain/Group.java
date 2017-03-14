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
@Entity(name = "sys_group")
public class Group extends AbstractPersistable<Long> {

    private String name;//给用户看

    @ManyToMany
    @JoinTable(name = "sys_group_role", joinColumns = {@JoinColumn(name = "group_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roleSet;

    @ManyToMany
    @JoinTable(name = "sys_group_per", joinColumns = {@JoinColumn(name = "group_id")}, inverseJoinColumns = {@JoinColumn(name = "per_id")})
    private Set<PermissionResources> permissionResourcesSet;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Role> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(Set<Role> roleSet) {
        this.roleSet = roleSet;
    }

    public Set<PermissionResources> getPermissionResourcesSet() {
        return permissionResourcesSet;
    }

    public void setPermissionResourcesSet(Set<PermissionResources> permissionResourcesSet) {
        this.permissionResourcesSet = permissionResourcesSet;
    }
}
