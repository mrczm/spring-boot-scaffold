package com.sj.modules.sys.web;

import com.sj.common.Result;
import com.sj.modules.sys.domain.Menu;
import com.sj.modules.sys.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.sj.common.ResultGenerator.ok;

/**
 * Created by yangrd on 2017/7/18.
 */
@RestController
@RequestMapping("/api/menu")
public class MenuControllers {

    @Autowired
    private MenuRepository menuRepository;

    @GetMapping
    public Result<List<Menu>> findAll() {
        return ok(menuRepository.findAll());
    }

}
