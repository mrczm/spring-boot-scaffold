package com.sj.modules.sys.repository;

import com.sj.modules.sys.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yangrd on 2017/7/14.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByLoginName(String loginName);
}
