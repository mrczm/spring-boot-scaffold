package com.sj.modules.sys.service;

import com.sj.common.utils.mapper.BeanMapper;
import com.sj.modules.sys.domain.Menu;
import com.sj.modules.sys.domain.Role;
import com.sj.modules.sys.domain.User;
import com.sj.modules.sys.repository.MenuRepository;
import com.sj.modules.sys.repository.UserRepository;
import com.sj.modules.sys.view.MenuTreeVO;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Security;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by yangrd on 2017/8/3.
 */
@Service
public class MenuTreeService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private UserRepository userRepository;

    public Collection<MenuTreeVO> listSystem() {
        return getMenuTree().getChildren();
    }

    public Collection<MenuTreeVO> listCurrentUserSystem() {
        //TODO 此处存在问题
        User user = userRepository.findByLoginName(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        Set<Menu> menuSet = user.getRoleSet().stream().flatMap(role -> role.getMenuSet().stream()).collect(Collectors.toSet());
        return getMenuTree(menuSet).getChildren();
    }

    public MenuTreeVO getMenuTree() {
        return getMenuTree(menuRepository.findAll());
    }

    public MenuTreeVO getMenuTree(Collection<Menu> menuList) {
        Map<Long, MenuTreeVO> menuTreeMap = menuList.stream().collect(Collectors.toMap(Menu::getId, o -> BeanMapper.map(o, MenuTreeVO.class)));
        menuTreeMap.forEach((k, v) -> {
            Long parentId = v.getParentId();
            if (parentId != 0) {
                menuTreeMap.get(parentId).addChildren(v);
            }
        });
        MenuTreeVO root = menuTreeMap.get(1L);
        return root;
    }

    private Menu getRoot() {
        return menuRepository.findByParentIsNull();
    }

}
