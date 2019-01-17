package com.sj.boot.modules.sys.repository;

import com.sj.boot.modules.sys.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * @author yangrd
 * @date 2019/1/9
 **/
public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户
     */
    Optional<User> findByUsername(String username);
}