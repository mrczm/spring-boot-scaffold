package com.sj.modules.sys.web;

import com.sj.modules.sys.domain.Menu;
import com.sj.modules.sys.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lhw on 2017/7/15.
 */
@RestController
@RequestMapping("menu")
public class MenuController {
    @Autowired
    MenuRepository menuRepository;

    @RequestMapping
    public ResponseEntity list() {
        List<Menu> menus = menuRepository.findByName("ROOT");
        menus.forEach(menu -> {
            List<Menu> menus1 = menuRepository.findByParent(menu).stream().filter(menu1 -> !menu1.getName().equals("ROOT")).collect(Collectors.toList());
            menu.setSubMenus(menus1);
            menus1.forEach(menu1 -> {
                List<Menu> menus2 = menuRepository.findByParent(menu1);
                menu1.setSubMenus(menus2);
            });
        });
        return ResponseEntity.ok(menus);
    }

    @GetMapping("{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        return ResponseEntity.ok(menuRepository.findOne(id));
    }

    @PostMapping("/do/{id}")
    public ResponseEntity addOrUpdate(@PathVariable Long id, Menu menu) {
        if (id != 0) {
            Menu tempMenu = menuRepository.findOne(id);
            tempMenu.setName(menu.getName());
            tempMenu.setIcon(menu.getIcon());
            tempMenu.setDescription(menu.getDescription());
            tempMenu.setUrl(menu.getUrl());
            tempMenu.setParent(menu.getParent());
            menu = menuRepository.save(tempMenu);
        } else {
            menu = menuRepository.save(menu);
        }
        return ResponseEntity.ok(menu);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        System.out.println("id " + id);
        Menu menu = menuRepository.findOne(id);
        menu.setParent(null);
        menuRepository.delete(menu);
        return ResponseEntity.ok(menu);
    }
}
