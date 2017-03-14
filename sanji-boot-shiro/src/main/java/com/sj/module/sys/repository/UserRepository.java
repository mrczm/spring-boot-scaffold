package com.sj.module.sys.repository;

import com.sj.module.sys.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by sunxyz on 2017/3/13.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByLoginName(String loginName);
}
