package com.sj.boot.modules.sys.repository;

import com.sj.boot.modules.sys.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author yangrd
 * @date 2019/1/9
 **/
public interface MenuRepository extends JpaRepository<Menu,Long> {

    /**
     * 根据父菜单获取子菜单
     * @param parent parent
     * @return child
     */
    List<Menu> findByParentOrderBySortAsc(Menu parent);
}
