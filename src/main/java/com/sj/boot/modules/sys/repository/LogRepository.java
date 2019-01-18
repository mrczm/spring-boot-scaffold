package com.sj.boot.modules.sys.repository;

import com.sj.boot.modules.sys.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yangrd
 * @date 2019/1/11
 **/
public interface LogRepository extends JpaRepository<Log, Long> {
}
