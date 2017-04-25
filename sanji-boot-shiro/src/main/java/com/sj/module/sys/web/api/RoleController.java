package com.sj.module.sys.web.api;

import com.sj.common.Result;
import com.sj.module.sys.constant.RequestConstant;
import com.sj.module.sys.domain.Menu;
import com.sj.module.sys.domain.Role;
import com.sj.module.sys.domain.User;
import com.sj.module.sys.repository.MenuRepository;
import com.sj.module.sys.repository.RoleRepository;
import com.sj.module.sys.repository.UserRepository;
import com.sj.module.sys.web.BaseController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by sunxyz on 2017/3/15.
 */
@Transactional
@RestController
@RequestMapping(RequestConstant.ROLE_API)
public class RoleController extends BaseController<RoleRepository, Role, Long> {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private UserRepository userRepository;

    @RequiresPermissions("sys:role:add")
    @PostMapping
    public Result<String> add(Role role, @RequestParam(value = "menuIds[]", required = false) String[] menus) {
        Set<Long> menuIdSet;
        try {
            menuIdSet = Arrays.stream(menus).map(id -> (Long.valueOf(id))).collect(Collectors.toSet());
        } catch (Exception e) {
            return Result.error();
        }
        Set<Menu> menuSet = menuRepository.findAll(menuIdSet).stream().collect(Collectors.toSet());
        role.setMenuSet(menuSet);
        return super.save(role);
    }

    @RequiresPermissions("sys:role:delete")
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @RequiresPermissions("sys:role:update")
    @PutMapping("/{id}")
    public Result<String> update(@PathVariable("id") Role old, Role role, @RequestParam(value = "menuIds[]", required = false) String[] menus) {
        Set<Long> menuIds = Arrays.stream(menus).map(id -> (Long.valueOf(id))).collect(Collectors.toSet());
        old.setName(null != role.getName() ? role.getName() : old.getName());
        old.setRoleType(null != role.getRoleType() ? role.getRoleType() : old.getRoleType());
        old.setDescription(null != role.getDescription() ? role.getDescription() : old.getDescription());
        if (null != menuIds) {
            Set<Menu> menuSet = menuRepository.findAll(menuIds).stream().collect(Collectors.toSet());
            old.setMenuSet(menuSet);
        }
        return super.update(old);
    }

    @RequiresPermissions("sys:role:view")
    @GetMapping
    public Page<Role> getAll(@RequestParam(name = "name", defaultValue = "") String name, Pageable pageable) {
        return repository.findByNameLike("%" + name + "%", pageable);
    }

    @Transactional(readOnly = true)
    @RequiresPermissions("sys:role:view")
    @GetMapping("/{id}/menu")
    public List<Long> listMenuId(@PathVariable("id") Role role) {
        if (null == role) {
            return null;
        }
        Set<Menu> menuSet = role.getMenuSet();
        if (null == menuSet) {
            return null;
        }
        return menuSet.parallelStream().map(Menu::getId).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @RequiresPermissions("sys:role:view")
    @GetMapping("/{id}/user")
    public Page<User> listUser(@PathVariable("id") Long roleId, @RequestParam(value = "name", defaultValue = "") String name, Pageable pageable) {
        return userRepository.findByRoleSetIdAndLoginNameLike(roleId, "%" + name + "%", pageable);
    }

    @RequiresPermissions("sys:role:delete")
    @DeleteMapping("{roleId}/user/{userId}")
    public Result<String> deleteUser(@PathVariable("roleId") Role role, @PathVariable("userId") User user) {
        Set<Role> roleSet = user.getRoleSet();
        roleSet.remove(role);
        return Result.ok();
    }
}
