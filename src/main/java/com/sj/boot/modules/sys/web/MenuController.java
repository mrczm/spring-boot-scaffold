package com.sj.boot.modules.sys.web;

import com.sj.boot.common.utils.BeanUtils;
import com.sj.boot.modules.sys.model.Menu;
import com.sj.boot.modules.sys.repository.MenuRepository;
import com.sj.boot.modules.sys.service.MenuTreeService;
import com.sj.boot.modules.sys.views.MenuTreeVO;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * 菜单控制器
 * @author yangrd
 * @date 2019/1/9
 **/
@RestController
@RequestMapping("/api/menus")
@AllArgsConstructor
public class MenuController {

    private MenuRepository repository;

    private MenuTreeService menuTreeService;

    @PostMapping
    public Menu add(@RequestBody Menu menu){
        return repository.save(menu);
    }

    @DeleteMapping
    @Transactional(rollbackFor=Exception.class)
    public void delete(@RequestBody List<Long> ids){
        repository.deleteInBatch(repository.findAllById(ids));
    }

    @PutMapping("{id}")
    public void update(@PathVariable("id") Menu old, @RequestBody Menu self){
        BeanUtils.copyProperties(self,old);
        repository.saveAndFlush(old);
    }

    @GetMapping
    public Collection<MenuTreeVO> listSystem() {
        return menuTreeService.listSystem();
    }

    @GetMapping("/{id}")
    public Menu get(@PathVariable("id") Menu menu){
        return menu;
    }

    @GetMapping("/{pid}/child")
    public List<Menu> findChildByParent(@PathVariable("pid") Menu parent){
        return repository.findByParentOrderBySortAsc(parent);
    }
}
