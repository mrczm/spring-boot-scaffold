package com.sj.module.sys.service;

import com.sj.module.sys.domain.Menu;
import com.sj.module.sys.domain.Role;
import com.sj.module.sys.domain.User;
import com.sj.module.sys.domain.vo.MenuTreeVO;
import com.sj.module.sys.repository.MenuRepository;
import com.sj.module.sys.repository.UserRepository;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    private static final Pageable PAGE_REQUEST = new PageRequest(0, 1, new Sort(Sort.Direction.DESC, "sort"));

    public Menu findByParentTop1(Menu parent) {
        Page<Menu> page = repository.findByParent(parent, PAGE_REQUEST);
        if (null != page) {
            List<Menu> list = page.getContent();
            if (null != list) {
                if (list.size() > 0) {
                    return list.get(0);
                }
            }
        }
        return null;
    }

    /**
     * TODO 此处需要加入缓冲
     * Created by sunxyz on 2017/3/15.
     */
    public Set<MenuTreeVO> getMenuTree() {
        Set<MenuTreeVO> treeSet = new TreeSet<>((obj1, obj2) -> obj1.getSort().compareTo(obj2.getSort()));
        Map<Long, Set<Menu>> menuLevel = getMenuLevel();
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
        User userInfo = userRepository.findByLoginName(getCurrentLoginName());
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

    private String getCurrentLoginName() {
        Subject subject = SecurityUtils.getSubject();
        PrincipalCollection principals = subject.getPrincipals();
        String currentLoginName = (String) principals.getPrimaryPrincipal();
        return currentLoginName;
    }
}
