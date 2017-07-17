package com.sj;

import com.sj.modules.sys.domain.Role;
import com.sj.modules.sys.domain.User;
import com.sj.modules.sys.repository.RoleRepository;
import com.sj.modules.sys.repository.UserDetailsRepository;
import com.sj.modules.sys.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by yangrd on 2017/7/3.
 */
@Component
public class DataInit {


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        User admin = userRepository.findByLoginName("admin");
        if (admin == null) {
            admin = new User();
            admin.setLoginName("admin");
            admin.setPassword(bCryptPasswordEncoder.encode("admin"));
            Role role = new Role();
            role.setRoleType("admin");
//            role = roleRepository.save(role);
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            admin.setRoleSet(roles);
            userRepository.save(admin);
        }
    }

}


