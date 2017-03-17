package com.sj.module.sys.web.api;

import com.sj.common.Result;
import com.sj.module.sys.domain.Menu;
import com.sj.module.sys.domain.Role;
import com.sj.module.sys.domain.User;
import com.sj.module.sys.repository.MenuRepository;
import com.sj.module.sys.repository.RoleRepository;
import com.sj.module.sys.repository.UserRepository;
import com.sj.module.sys.web.BaseController;
import org.apache.shiro.SecurityUtils;
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
@RestController
@RequestMapping("/api/sys/role")
public class RoleController extends BaseController<RoleRepository, Role, Long> {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public Result<String> add(Role role, @RequestParam(value = "meunId", required = false) List<String> meunIds, @RequestParam(value = "meunIds[]", required = false) String[] menus) {
        try {
            Set<Long> meunIdSet;
            //接受 meunId = 1 & meunId = 2 || menuIds[] = [1,2,3]
            //两种方式的一种
            if (menus != null) {
                meunIdSet = Arrays.stream(menus).map(id -> (Long.valueOf(id))).collect(Collectors.toSet());
            } else {
                meunIdSet = meunIds.stream().map(id -> (Long.valueOf(id))).collect(Collectors.toSet());
            }
            Set<Menu> menuSet = menuRepository.findAll(meunIdSet).stream().collect(Collectors.toSet());
            role.setMenuSet(menuSet);
        } catch (Exception e) {
            return Result.error();
        }
        return super.save(role);
    }

    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @PutMapping("/{id}")
    public Result<String> update(@PathVariable("id") Role old, Role role, @RequestParam(value = "meunIds[]", required = false) String[] menus) {
        Set<Long> meunIds = Arrays.stream(menus).map(id -> (Long.valueOf(id))).collect(Collectors.toSet());
        old.setName(null != role.getName() ? role.getName() : old.getName());
        old.setRoleType(null != role.getRoleType() ? role.getRoleType() : old.getRoleType());
        old.setDescription(null != role.getDescription() ? role.getDescription() : old.getDescription());
        if (null != meunIds) {
            Set<Menu> menuSet = menuRepository.findAll(meunIds).stream().collect(Collectors.toSet());
            old.setMenuSet(menuSet);
        }
        return super.update(old);
    }

    @GetMapping
    public Page<Role> getAll(@RequestParam(name = "name", defaultValue = "") String name, Pageable pageable) {
        return repository.findByNameLike("%" + name + "%", pageable);
    }

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

    @GetMapping("/{id}/user")
    public Page<User> listUser(@PathVariable("id") Role role, @RequestParam(value = "name", defaultValue = "") String name, Pageable pageable) {
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        return userRepository.findByRoleSetAndLoginNameLike(roles, "%" + name + "%", pageable);
    }

    @Transactional
    @DeleteMapping("{roleId}/user/{userId}")
    public Result<String> deleteUser(@PathVariable("roleId") Role role, @PathVariable("userId") User user) {
        Set<Role> roleSet = user.getRoleSet();
        roleSet.remove(role);
        return Result.ok();
    }
}
