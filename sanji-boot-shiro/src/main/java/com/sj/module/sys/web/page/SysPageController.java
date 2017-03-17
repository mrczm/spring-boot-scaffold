package com.sj.module.sys.web.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by sunxyz on 2017/3/17.
 */
@Controller
@RequestMapping("/page/sys")
public class SysPageController {

    private static final String TEMPLATE_PATH = "/pages/sys/";

    @GetMapping("/menu")
    public String menuPage(){
        return TEMPLATE_PATH+"menu";
    }

    @GetMapping("/menu/frame")
    public String menuFramePage(){
        return TEMPLATE_PATH+"menu_frame";
    }

    @GetMapping("/role")
    public String rolePage(){
        return TEMPLATE_PATH+"role";
    }

    @GetMapping("/role/frame")
    public String roleFramePage(){
        return TEMPLATE_PATH+"role_frame";
    }


    @GetMapping("/role/user")
    public String roleUserPage(){
        return TEMPLATE_PATH+"role_user";
    }

}
