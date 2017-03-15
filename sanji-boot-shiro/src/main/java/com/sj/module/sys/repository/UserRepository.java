package com.sj.module.sys.repository;

import com.sj.module.sys.domain.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by sunxyz on 2017/3/13.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    @Cacheable(value = "users", key = "'demoInfo_'+#p0")
    User findByLoginName(String loginName);
}
