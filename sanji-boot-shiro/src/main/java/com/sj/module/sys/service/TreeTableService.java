package com.sj.module.sys.service;

import com.alibaba.fastjson.JSON;
import com.sj.module.sys.domain.Menu;
import com.sj.module.sys.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by sunxyz on 2017/3/15.
 */
@Service
public class TreeTableService {

    @Autowired
    private MenuRepository repository;

    /**
     * 对树进行展平
     * Created by sunxyz on 2017/3/15.
     */
    public List<Menu> listFlatMenuTree() {
        Set<Tree> treeSet = getTree();
        List<Menu> flatMenuList = new ArrayList<>();
        treeSet.forEach(tree -> {
            addFlatMenu(tree, flatMenuList);
        });
        return flatMenuList;
    }

    private void addFlatMenu(Tree tree, List<Menu> flatMenuList) {
        flatMenuList.add(tree.getSelf());
        Set<Tree> treeSet = tree.getChild();
        treeSet.forEach(tree1 -> {
            this.addFlatMenu(tree1, flatMenuList);
        });
    }

    private Set<Tree> getTree() {
        Set<Tree> treeSet = new TreeSet<>((obj1, obj2) -> obj1.getSelf().getSort().compareTo(obj2.getSelf().getSort()));
        Map<Long, Set<Menu>> menuLevel = getAllMenuLevel();//获取所有的菜单
        Map<Long, Tree> menuTreeVOMap = new HashMap<>();
        for (Map.Entry<Long, Set<Menu>> entry : menuLevel.entrySet()) {
            Set<Menu> menuSet = entry.getValue();
            if (treeSet.isEmpty()) {//顶级菜单
                menuSet.forEach(menu -> {
                    Tree tree = new Tree(menu);
                    treeSet.add(tree);
                    menuTreeVOMap.put(menu.getId(), tree);
                });
            } else {//子菜单
                menuSet.forEach(menu -> {
                    Tree tree = new Tree(menu);
                    menuTreeVOMap.put(menu.getId(), tree);
                    Tree parentTree = menuTreeVOMap.get(menu.getParent().getId());
                    if (parentTree != null) {
                        parentTree.getChild().add(tree);
                    }
                });
            }
        }
        return treeSet;
    }

    private Map<Long, Set<Menu>> getAllMenuLevel() {
        Map<Long, Set<Menu>> menuLevel = new TreeMap<>();
        List<Menu> menuSet = repository.findAll();
        if (null != menuSet) {
            menuSet.forEach(menu -> {
                Long level = menu.getLevel();
                Set<Menu> menuLevelSet = menuLevel.get(level);
                if (null == menuLevelSet) {
                    menuLevelSet = new HashSet<>();
                    menuLevel.put(level, menuLevelSet);
                }
                menuLevelSet.add(menu);
            });
        }
        return menuLevel;
    }

    class Tree {
        private Menu self;

        private Set<Tree> child;

        {
            child = new TreeSet<>((obj1, obj2) -> obj1.getSelf().getSort().compareTo(obj2.getSelf().getSort()));// 升序排序
        }

        public Tree(Menu self) {
            this.self = self;
        }

        public Menu getSelf() {
            return self;
        }

        public void setSelf(Menu self) {
            this.self = self;
        }

        public Set<Tree> getChild() {
            return child;
        }

        public void setChild(Set<Tree> child) {
            this.child = child;
        }
    }
}
