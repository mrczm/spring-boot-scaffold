package com.sj.modules.sys.web;

import com.sj.common.Result;
import com.sj.modules.sys.domain.Menu;
import com.sj.modules.sys.repository.MenuRepository;
import com.sj.modules.sys.service.MenuTreeService;
import com.sj.modules.sys.view.MenuTreeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static com.sj.common.ResultGenerator.error;
import static com.sj.common.ResultGenerator.ok;

/**
 * Created by yangrd on 2017/7/18.
 */
@RestController
@RequestMapping("/api/menu")
public class MenuControllers {

    @Autowired
    private MenuRepository repository;

    @Autowired
    private MenuTreeService menuTreeService;

    @GetMapping
    public Result<Collection<MenuTreeVO>> findAll() {
        return ok(menuTreeService.listSystem());
    }

    @GetMapping("{pid}/child")
    public Result<List<Menu>> findByParent(@PathVariable("pid") Menu parent) {
        if (Objects.isNull(parent)) {
            return error("pid 不存在");
        }
        return ok(repository.findByParentOrderBySortAsc(parent));
    }

    @PostMapping("{pid}")
    public Result<Menu> add(@PathVariable("pid") Menu parent, @RequestBody Menu menu) {
        if (Objects.isNull(parent)) {
            return error("pid 不存在");
        }
        menu.setParent(parent);
        return ok(repository.save(menu));
    }

    @PutMapping("{id}")
    public Result<Menu> update(@PathVariable("id") Menu self, @RequestBody Menu menu) {
        if (Objects.isNull(self)) {
            return error("pid 不存在");
        }
        self.setName(menu.getName());
        return ok(repository.save(self));
    }

}
