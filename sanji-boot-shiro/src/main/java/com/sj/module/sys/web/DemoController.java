package com.sj.module.sys.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by sunxyz on 2017/3/13.
 */
@Controller
public class DemoController {

    @RequiresAuthentication
    @GetMapping({"/index", "/"})
    public String index(Model model) {
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("username", userName);
        return "index";
    }

    @RequiresPermissions("sys:view")//表示当前Subject需要权限account:create
    @GetMapping("/test")
    public String test(Model model) {
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("username", userName);
        return "index";
    }
}
