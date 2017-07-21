package com.sj.modules.sys.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sj.modules.sys.domain.Menu;
import com.sj.modules.sys.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        List<Menu> rootMenus = new ArrayList<>();
        Menu menu = menuRepository.findByName("ROOT");
        rootMenus.add(menu);
        setSubMenus(menu);
        return ResponseEntity.ok(rootMenus);
    }

    private void setSubMenus(Menu menu) {
        List<Menu> subMenus = menuRepository.findByParentOrderBySortAsc(menu);
        menu.setSubMenus(subMenus);
        subMenus.forEach(subMenu -> {
            setSubMenus(subMenu);
        });
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
        Menu menu = menuRepository.findOne(id);
        menu.setParent(null);
        menuRepository.delete(menu);
        return ResponseEntity.ok(menu);
    }

    @PostMapping("/synchronize")
    public ResponseEntity synchronize(String json) {
        JSONArray array = JSON.parseArray(json);
        updateSort(array, menuRepository.findByName("ROOT"));
        return ResponseEntity.ok(json);
    }

    private void updateSort(JSONArray array, Menu parent) {
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = (JSONObject) array.get(i);
            Menu menu = menuRepository.findOne(object.getLong("id"));
            if ("ROOT".equals(menu.getName()))
                menu.setParent(null);
            else
                menu.setParent(parent);
            menu.setSort((long) i);
            menuRepository.save(menu);
            JSONArray subArray = object.getJSONArray("children");
            if (!Objects.isNull(subArray))
                updateSort(subArray, menu);
        }
    }
}
