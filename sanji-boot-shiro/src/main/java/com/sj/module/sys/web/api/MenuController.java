package com.sj.module.sys.web.api;

import com.alibaba.fastjson.JSON;
import com.sj.common.HttpResponse;
import com.sj.module.sys.domain.Menu;
import com.sj.module.sys.domain.Role;
import com.sj.module.sys.domain.User;
import com.sj.module.sys.domain.vo.MenuTreeVO;
import com.sj.module.sys.repository.MenuRepository;
import com.sj.module.sys.repository.UserRepository;
import com.sj.module.sys.service.MenuService;
import com.sj.module.sys.service.TreeService;
import com.sj.module.sys.web.BaseController;
import javafx.collections.transformation.SortedList;
import org.apache.commons.collections.list.TreeList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by sunxyz on 2017/3/14.
 */
@RestController
@RequestMapping("/api/menu")
public class MenuController extends BaseController<MenuRepository, Menu, Long> {

    @Autowired
    private MenuService menuService;

    @Autowired
    private TreeService treeService;

    @Transactional
    @PostMapping
    public HttpResponse<String> add(@RequestParam(name = "pid", defaultValue = "1") Menu parent, Menu menu) {
        menu.setParent(parent);
        menu.setLevel(parent.getLevel() + 1);
        return super.save(menu);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public HttpResponse<String> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @Transactional
    @PutMapping("/{id}")
    public HttpResponse<String> update(@PathVariable("id") Menu old, Menu menu) {
        old.setName(null != menu ? menu.getName() : old.getName());
        old.setDescription(null != menu ? menu.getDescription() : old.getDescription());
        old.setUrl(null != menu ? menu.getUrl() : old.getUrl());
        old.setIcon(null != menu ? menu.getIcon() : old.getIcon());
        old.setSort(null != menu ? menu.getSort() : old.getSort());
        old.setPermission(null != menu ? menu.getPermission() : old.getPermission());
        return super.update(old);
    }

    //给 treeTable 使用
    @GetMapping
    public List<Menu> getTreeTable() {
        return treeService.listFlatMenuTree();
    }

    //给 zTree
    @GetMapping("/all")
    public List<Menu> getAll() {
        return repository.findAll();
    }

    //TODO 树形菜单构建
    @GetMapping("/tree")
    public Set<MenuTreeVO> getTree(Pageable pageable) {
        return menuService.getMenuTree();
    }


}
