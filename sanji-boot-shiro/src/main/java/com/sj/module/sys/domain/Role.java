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
@Entity(name = "sys_role")
public class Role extends AbstractPersistable<Long> {

    private String name;//给用户看

    private String roleName;//角色名称，shiro将通过角色名来进行鉴权

    @ManyToMany
    @JoinTable(name = "sys_role_per", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "per_id")})
    private Set<PermissionResources> permissionResourcesSet;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<PermissionResources> getPermissionResourcesSet() {
        return permissionResourcesSet;
    }

    public void setPermissionResourcesSet(Set<PermissionResources> permissionResourcesSet) {
        this.permissionResourcesSet = permissionResourcesSet;
    }
}
