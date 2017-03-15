package com.sj.module.sys.service;

import com.sj.module.sys.domain.Menu;
import com.sj.module.sys.domain.Role;
import com.sj.module.sys.domain.User;
import com.sj.module.sys.domain.vo.MenuTreeVO;
import com.sj.module.sys.repository.MenuRepository;
import com.sj.module.sys.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by sunxyz on 2017/3/15.
 */
@Service
public class MenuService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuRepository repository;

    /**
     * TODO 此处需要加入缓冲
     * Created by sunxyz on 2017/3/15.
     */
    public Set<MenuTreeVO> getMenuTree() {
        Set<MenuTreeVO> treeSet = new TreeSet<>((obj1, obj2) -> obj1.getSort().compareTo(obj2.getSort()));
        Map<Long, Set<Menu>> menuLevel = getMenuLevelTest();
        Map<Long, MenuTreeVO> menuTreeVOMap = new HashMap<>();
        for (Map.Entry<Long, Set<Menu>> entry : menuLevel.entrySet()) {
            Set<Menu> menuSet = entry.getValue();
            if (treeSet.isEmpty()) {//顶级菜单
                menuSet.forEach(menu -> {
                    MenuTreeVO menuTreeVO = new MenuTreeVO(menu.getName(), menu.getIcon(), menu.getUrl(), menu.getDescription(), null == menu.getSort() ? 0 : menu.getSort());
                    treeSet.add(menuTreeVO);
                    menuTreeVOMap.put(menu.getId(), menuTreeVO);
                });
            } else {//子菜单
                menuSet.forEach(menu -> {
                    MenuTreeVO menuTreeVO = new MenuTreeVO(menu.getName(), menu.getIcon(), menu.getUrl(), menu.getDescription(), null == menu.getSort() ? 0 : menu.getSort());
                    menuTreeVOMap.put(menu.getId(), menuTreeVO);
                    MenuTreeVO parentMenuTree = menuTreeVOMap.get(menu.getParent().getId());
                    parentMenuTree.getMenuTrees().add(menuTreeVO);
                });
            }
        }
        return treeSet;
    }

    /**
     * TODO 暂时没有组的概念
     * Created by sunxyz on 2017/3/14.
     */
    private Map<Long, Set<Menu>> getMenuLevel() {
        User userInfo = userRepository.findOne(getUserId());
        Set<Role> roleSet = userInfo.getRoleSet();
        Map<Long, Set<Menu>> menuLevel = new TreeMap<>();
        if (null != roleSet) {
            roleSet.forEach(role -> {
                Set<Menu> menuSet = role.getMenuSet();
                if (null != menuSet) {
                    menuSet.forEach(menu -> {
                        Long level = menu.getLevel();
                        Set<Menu> menuLevelSet = menuLevel.get(level);
                        if (menuLevelSet == null) {
                            menuLevelSet = new HashSet<>();
                            menuLevel.put(level, menuLevelSet);
                        }
                        menuLevelSet.add(menu);
                    });
                }
            });
        }
        return menuLevel;
    }

    private Map<Long, Set<Menu>> getMenuLevelTest() {
        Map<Long, Set<Menu>> menuLevel = new TreeMap<>();
        List<Menu> menuSet = repository.findAll();
        if (null != menuSet) {
            menuSet.forEach(menu -> {
                Long level = menu.getLevel();
                Set<Menu> menuLevelSet = menuLevel.get(level);
                if (menuLevelSet == null) {
                    menuLevelSet = new HashSet<>();
                    menuLevel.put(level, menuLevelSet);
                }
                menuLevelSet.add(menu);
            });
        }
        return menuLevel;
    }

    private Long getUserId() {
        return 0L;
    }
}
