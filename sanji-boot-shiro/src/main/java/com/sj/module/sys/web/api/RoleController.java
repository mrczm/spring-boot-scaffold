package com.sj.module.sys.web.api;

import com.sj.common.HttpResponse;
import com.sj.module.sys.domain.Menu;
import com.sj.module.sys.domain.Role;
import com.sj.module.sys.repository.MenuRepository;
import com.sj.module.sys.repository.RoleRepository;
import com.sj.module.sys.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by sunxyz on 2017/3/15.
 */
@RestController
@RequestMapping("/api/role")
public class RoleController extends BaseController<RoleRepository, Role, Long> {

    @Autowired
    private MenuRepository menuRepository;

    @PostMapping
    public HttpResponse<String> add(Role role, @RequestParam(value = "meunId", required = false) List<String> meunIds, @RequestParam(value = "meunIds[]", required = false) String[] menus) {
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
            return HttpResponse.error();
        }
        return super.save(role);
    }

    @DeleteMapping("/{id}")
    public HttpResponse<String> delete(Long id) {
        return super.delete(id).orGetErrorMsg("有用户正在使用该菜单，不能进行删除");
    }

    @PutMapping("/{id}")
    public HttpResponse<String> update(@PathVariable("id") Role old, Role role, @RequestBody(required = false) Set<Long> meunIds) {
        old.setName(null != role.getName() ? role.getName() : old.getName());
        old.setRoleType(null != role.getRoleType() ? role.getRoleType() : old.getRoleType());
        old.setDescription(null != role.getDescription() ? role.getDescription() : old.getDescription());
        if (null != meunIds) {
            Set<Menu> menuSet = menuRepository.findAll(meunIds).stream().collect(Collectors.toSet());
            role.setMenuSet(menuSet);
        }
        return super.save(role);
    }

    @GetMapping
    public Page<Role> getAll(@RequestParam(name = "name", defaultValue = "") String name, Pageable pageable) {
        return repository.findByNameLike("%" + name + "%", pageable);
    }

}
