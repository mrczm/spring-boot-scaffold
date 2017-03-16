package com.sj;

import com.sj.module.sys.domain.Menu;
import com.sj.module.sys.domain.Role;
import com.sj.module.sys.domain.User;
import com.sj.module.sys.repository.MenuRepository;
import com.sj.module.sys.repository.UserRepository;
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
    private UserRepository userRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public void run(String... args) throws Exception {
        User user = userRepository.findByLoginName("admin");
        Menu menu = menuRepository.findOne(1L);
        Date now = new Date();
        if(null == user){
            user = new User();
            user.setLoginName("admin");
            user.setPassword("admin");
            user.setCreateTime(now);
            user.setUpdateTime(now);
            Set<Role> roleSet = new HashSet<>();
            Role role = new Role();
            role.setName("ADMIN");
            role.setRoleType("ADMIN");
            role.setCreateTime(now);
            role.setUpdateTime(now);
            roleSet.add(role);
            user.setRoleSet(roleSet);
            userRepository.save(user);
            logger.info("初始化基础数据完成");
        }
        if(null == menu){
            menu = new Menu();
            menu.setName("ROOT");
            menu.setLevel(0L);
            menu.setCreateTime(now);
            menu.setUpdateTime(now);
            menuRepository.save(menu);
        }

    }
}
