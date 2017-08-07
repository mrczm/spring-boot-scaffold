package com.sj.modules.sys.web;

import com.sj.common.Result;
import com.sj.modules.sys.domain.Menu;
import com.sj.modules.sys.domain.UserDetails;
import com.sj.modules.sys.repository.MenuRepository;
import com.sj.modules.sys.service.MenuTreeService;
import com.sj.modules.sys.view.MenuTreeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Supplier;

import static com.sj.common.ResultGenerator.error;
import static com.sj.common.ResultGenerator.ok;

/**
 * TODO 更新删除未实现
 * Created by yangrd on 2017/7/18.
 */
@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Autowired
    private MenuRepository repository;

    @Autowired
    private MenuTreeService menuTreeService;

    @PostMapping
    public Result<Menu> add(@RequestBody Menu menu) {
        Long pid = menu.getParent().getId();
        Menu parent = repository.findOne(pid);
        if (Objects.isNull(parent)) {
            return error("pid 不存在");
        }
        Date now = new Date();
        menu.setModifiedTime(now);
        menu.setCreatedTime(now);
        menu.setParent(parent);
        return ok(repository.save(menu));
    }

    @Transactional
    @DeleteMapping
    public Result<String> delete(@RequestBody Long[] ids) {
        Arrays.asList(ids).forEach(repository::delete);
        return ok();
    }

    @PutMapping("{id}")
    public Result<Menu> update(@PathVariable("id") Menu old, @RequestBody Menu self) {
        if (Objects.isNull(self)) {
            return error("id 不存在");
        }
        updateVal(old, self);
        return ok(repository.save(old));
    }

    @GetMapping
    public Result<Collection<MenuTreeVO>> listSystem() {
        return ok(menuTreeService.listSystem());
    }

    @GetMapping("/{id}")
    public Result<Menu> get(@PathVariable("id") Menu menu) {
        return ok(menu);
    }

    @GetMapping("{pid}/child")
    public Result<List<Menu>> findByParent(@PathVariable("pid") Menu parent) {
        if (Objects.isNull(parent)) {
            return error("pid 不存在");
        }
        return ok(repository.findByParentOrderBySortAsc(parent));
    }

    private void updateVal(Menu old, Menu self) {
        Date now = new Date();
        old.setModifiedTime(now);
        old.setName(val(old::getName, self::getName));
        old.setIcon(val(old::getIcon, self::getIcon));
        old.setParent(val(old::getParent, self::getParent));
        old.setSort(val(old::getSort, self::getSort));
        old.setDepth(val(old::getDepth, self::getDepth));
        old.setPermission(val(old::getPermission, self::getPermission));
    }

    private <T> T val(Supplier<T> oldVal, Supplier<T> newVal) {
        T oldV = oldVal.get();
        T newV = newVal.get();
        return Objects.nonNull(newV) ? newV : oldV;
    }
}
