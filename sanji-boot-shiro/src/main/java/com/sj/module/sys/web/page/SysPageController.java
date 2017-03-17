package com.sj.module.sys.web.page;

import com.sj.module.sys.domain.Role;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by sunxyz on 2017/3/17.
 */
@Controller
@RequestMapping("/page/sys")
public class SysPageController {

    private static final String TEMPLATE_PATH = "/pages/sys/";

    @RequiresPermissions("sys:menu:view")
    @GetMapping("/menu")
    public String menuPage() {
        return TEMPLATE_PATH + "menu";
    }

    @RequiresPermissions("sys:menu:frame:view")
    @GetMapping("/menu/frame")
    public String menuFramePage() {
        return TEMPLATE_PATH + "menu_frame";
    }

    @RequiresPermissions("sys:role:view")
    @GetMapping("/role")
    public String rolePage() {
        return TEMPLATE_PATH + "role";
    }

    @RequiresPermissions("sys:role:frame:view")
    @GetMapping("/role/frame")
    public String roleFramePage() {
        return TEMPLATE_PATH + "role_frame";
    }

    @RequiresPermissions("sys:role:user:view")
    @GetMapping("/role/{roleId}/user")
    public String roleUserPage(@PathVariable("roleId") Role role, Model model) {
        model.addAttribute("role", role);
        return TEMPLATE_PATH + "role_user";
    }

}
