package com.sj.modules.sys.web;

import com.alibaba.fastjson.JSON;
import com.sj.modules.sys.domain.Menu;
import com.sj.modules.sys.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        List<Menu> menus = menuRepository.findAll();
        System.out.println("menus " + JSON.toJSONString(menus));
        return ResponseEntity.ok(menus);
    }

    @RequestMapping("{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        return ResponseEntity.ok(menuRepository.findOne(id));
    }
}
