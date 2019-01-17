package com.sj.boot.modules.sys.service;

import com.sj.boot.common.utils.mapper.BeanMapper;
import com.sj.boot.modules.sys.model.Menu;
import com.sj.boot.modules.sys.model.User;
import com.sj.boot.modules.sys.repository.MenuRepository;
import com.sj.boot.modules.sys.repository.UserRepository;
import com.sj.boot.modules.sys.views.MenuTreeVO;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author yangrd
 * @date 2019/1/11
 **/
@Service
@AllArgsConstructor
public class MenuTreeService {

    private MenuRepository menuRepository;

    private UserRepository userRepository;

    /**
     * 获取菜单
     */
    public Collection<MenuTreeVO> listSystem() {
        return getMenuTree().getChildren();
    }

    /**
     *
     需要用户登陆成功才能使用
     */
    public Collection<MenuTreeVO> listCurrentUserSystem() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<Menu> menuSet = userRepository.findByUsername(user.getUsername()).map(user1->user1.getRoleSet().stream().flatMap(role -> role.getMenuSet().stream()).collect(Collectors.toSet())).orElseThrow(RuntimeException::new);
        return getMenuTree(menuSet).getChildren();
    }

    /**
     * 获取菜单树
     */
    private MenuTreeVO getMenuTree() {
        return getMenuTree(menuRepository.findAll());
    }

    private MenuTreeVO getMenuTree(Collection<Menu> menuCollection) {
        Map<Long, MenuTreeVO> menuTreeMap = menuCollection.stream().collect(Collectors.toMap(Menu::getId, o -> BeanMapper.map(o, MenuTreeVO.class)));
        menuTreeMap.forEach((k, v) -> {
            Long parentId = v.getParentId();
            if (parentId != -1) {
                menuTreeMap.get(parentId).addChildren(v);
            }
        });
        return menuTreeMap.get(1L);
    }
}
