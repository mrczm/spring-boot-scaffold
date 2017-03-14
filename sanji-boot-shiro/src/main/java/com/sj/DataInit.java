package com.sj;

import com.sj.module.sys.domain.Role;
import com.sj.module.sys.domain.User;
import com.sj.module.sys.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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

    @Override
    public void run(String... args) throws Exception {
        User user = userRepository.findByLoginName("admin");
        if(null == user){
            user = new User();
            user.setLoginName("admin");
            user.setPassword("admin");
            Set<Role> roleSet = new HashSet<>();
            Role role = new Role();
            role.setName("ADMIN");
            roleSet.add(role);
            user.setRoleSet(roleSet);
            userRepository.save(user);
            logger.info("初始化基础数据完成");
        }

    }
}
