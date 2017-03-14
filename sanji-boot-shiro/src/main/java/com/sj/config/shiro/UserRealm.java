package com.sj.config.shiro;

import com.sj.module.sys.domain.Group;
import com.sj.module.sys.domain.PermissionResources;
import com.sj.module.sys.domain.Role;
import com.sj.module.sys.domain.User;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by sunxyz on 2017/3/13.
 */
public class UserRealm extends AbstractUserRealm {

    @Override
    public UserRolesAndPermissions doGetGroupAuthorizationInfo(User userInfo) {
        Set<String> userRoles = new HashSet<>();
        Set<String> userPermissions = new HashSet<>();
        //获取当前用户下所有ACL权限列表 待续。。。
        //获取当前用户下拥有的所有角色列表
        Set<Role> roles = userInfo.getRoleSet();
        loadRolesAndPermissions(userRoles, userPermissions, roles);
        return new UserRolesAndPermissions(userRoles, userPermissions);
    }

    @Override
    public UserRolesAndPermissions doGetRoleAuthorizationInfo(User userInfo) {
        Set<String> userRoles = new HashSet<>();
        Set<String> userPermissions = new HashSet<>();
        Group group = userInfo.getGroup();
        if (null != group) {
            Set<Role> roles = group.getRoleSet();
            loadRolesAndPermissions(userRoles, userPermissions, roles);
            Set<PermissionResources> permissionResourcesSet = group.getPermissionResourcesSet();
            if (permissionResourcesSet != null) {
                Set<String> permissionResourcesNameSet = permissionResourcesSet.stream().map(PermissionResources::getName).collect(Collectors.toSet());
                userPermissions.addAll(permissionResourcesNameSet);
            }
        }
        return new UserRolesAndPermissions(userRoles, userPermissions);
    }

    private void loadRolesAndPermissions(Set<String> userRoles, Set<String> userPermissions, Set<Role> roles) {
        if (null != roles) {
            roles.forEach(role -> {
                userRoles.add(role.getName());
                Set<PermissionResources> permissionResourcesSet = role.getPermissionResourcesSet();
                if (null != permissionResourcesSet) {
                    Set<String> permissionResourcesNameSet = permissionResourcesSet.stream().map(PermissionResources::getName).collect(Collectors.toSet());
                    userPermissions.addAll(permissionResourcesNameSet);
                }
            });
        }
    }
}