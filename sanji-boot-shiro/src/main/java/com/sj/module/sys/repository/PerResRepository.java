package com.sj.module.sys.repository;

import com.sj.module.sys.domain.PermissionResources;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by sunxyz on 2017/3/13.
 */
public interface PerResRepository extends JpaRepository<PermissionResources, Long> {
}
