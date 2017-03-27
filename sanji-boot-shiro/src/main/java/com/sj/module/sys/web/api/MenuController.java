package com.sj.module.sys.web.api;

import com.sj.common.Result;
import com.sj.module.sys.domain.Menu;
import com.sj.module.sys.domain.vo.MenuTreeVO;
import com.sj.module.sys.repository.MenuRepository;
import com.sj.module.sys.service.MenuService;
import com.sj.module.sys.service.TreeTableService;
import com.sj.module.sys.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by sunxyz on 2017/3/14.
 */
@RestController
@RequestMapping("/api/sys/menu")
public class MenuController extends BaseController<MenuRepository, Menu, Long> {

    @Autowired
    private MenuService menuService;

    @Autowired
    private TreeTableService treeService;

    @RequiresPermissions("sys:menu:add")
    @Transactional
    @PostMapping
    public Result<String> add(@RequestParam(name = "pid", defaultValue = "1") Menu parent, Menu menu) {
        menu.setParent(parent);
        //层级添加+排序添加
        menu.setLevel(parent.getLevel() + 1);
        menu.setSort(null != menu.getSort() ? menu.getSort() : 0L);
        menu.setVisible(null != menu.getVisible() ? menu.getVisible() : false);
        if (menu.getSort() == 0L) {
            Menu brothers = menuService.findByParentTop1(parent);
            if (null != brothers) {
                menu.setSort(brothers.getSort() + 1);
            } else {
                menu.setSort(parent.getSort() * 10 + 1);
            }
        }
        return super.save(menu);
    }

    @RequiresPermissions("sys:menu:delete")
    @Transactional
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @RequiresPermissions("sys:menu:update")
    @Transactional
    @PutMapping("/{id}")
    public Result<String> update(@PathVariable("id") Menu old, Menu menu) {
        Menu news = valueUpdate(old, menu);
        return super.update(news);
    }

    //给 treeTable 使用
    @RequiresPermissions("sys:menu:view")
    @GetMapping
    public List<Menu> getTreeTable() {
        return treeService.listFlatMenuTree();
    }

    //给 zTree
    @RequiresPermissions("sys:menu:view")
    @GetMapping("/all")
    public List<Menu> getAll() {
        return repository.findAll();
    }


    //--需要用户登录之后使用 树形菜单构建 需要根据用户调整
    @GetMapping("/tree")
    public Set<MenuTreeVO> getTreeByCurrentUser() {
        return menuService.getMenuTreeByCurrentUser();
    }

    private Menu valueUpdate(Menu old, Menu menu) {
        old.setName(null != menu ? menu.getName() : old.getName());
        old.setDescription(null != menu ? menu.getDescription() : old.getDescription());
        old.setUrl(null != menu ? menu.getUrl() : old.getUrl());
        old.setIcon(null != menu ? menu.getIcon() : old.getIcon());
        old.setSort(null != menu ? menu.getSort() : old.getSort());
        old.setPermission(null != menu ? menu.getPermission() : old.getPermission());
        old.setVisible(null != menu.getVisible() ? menu.getVisible() : false);
        return old;
    }

}
