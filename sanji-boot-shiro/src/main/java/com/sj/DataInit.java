package com.sj;

import com.sj.common.utils.EncryptionUtils;
import com.sj.module.sys.domain.Menu;
import com.sj.module.sys.domain.Role;
import com.sj.module.sys.domain.User;
import com.sj.module.sys.repository.MenuRepository;
import com.sj.module.sys.repository.UserRepository;
import com.sj.module.sys.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sunxyz on 2017/3/13.
 */
@Component
public class DataInit implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInit.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public void run(String... args) throws Exception {
        Menu root = menuRepository.findOne(1L);
        User user = userService.findByLoginName("admin");
        Date now = new Date();
        if (null == user || null == root) {
            root = buildMenu("root", "", 0L, "", null);
            root = menuRepository.save(root);
            Menu menu_1 = buildMenu("sys", "", 1L, "", root);
            menu_1 = menuRepository.save(menu_1);
            Menu menu_1_1 = buildMenu("菜单管理", "page/sys/menu", 11L, "sys:menu", menu_1);
            menuRepository.save(menu_1_1);
            Menu menu_1_2 = buildMenu("角色管理", "page/sys/role", 12L, "sys:role", menu_1);
            menuRepository.save(menu_1_2);
            user = new User();
            user.setLoginName("admin");
            user.setPassword("admin");
            user.setCreateTime(now);
            user.setUpdateTime(now);
            Set<Role> roleSet = new HashSet<>();
            Role role = new Role();
            role.setName("ADMIN");
            role.setRoleType("ADMIN");
            Set<Menu> menus = new HashSet<>();
            menus.add(root);
            menus.add(menu_1);
            menus.add(menu_1_1);
            menus.add(menu_1_2);
            role.setMenuSet(menus);
            role.setCreateTime(now);
            role.setUpdateTime(now);
            roleSet.add(role);
            user.setRoleSet(roleSet);
            userService.save(user);
            logger.info("初始化基础数据完成");
        }

    }

    private Menu buildMenu(String name, String url, Long sort, String per, Menu parent) {
        Date now = new Date();
        Menu menu = new Menu();
        menu.setName(name);
        menu.setUrl(url);
        menu.setPermission(per);
        if (null != parent) {
            menu.setParent(parent);
            menu.setLevel(parent.getLevel() + 1);
            menu.setSort(sort);
        } else {
            menu.setLevel(0L);
            menu.setSort(0L);
        }
        menu.setCreateTime(now);
        menu.setUpdateTime(now);
        menu.setVisible(true);
        return menu;
    }
}
