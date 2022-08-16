package com.weiho.scaffold.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.weiho.scaffold.mp.service.impl.CommonServiceImpl;
import com.weiho.scaffold.system.entity.Menu;
import com.weiho.scaffold.system.entity.Role;
import com.weiho.scaffold.system.entity.convert.MenuConvert;
import com.weiho.scaffold.system.entity.dto.MenuDTO;
import com.weiho.scaffold.system.entity.vo.MenuMetaVO;
import com.weiho.scaffold.system.entity.vo.MenuVO;
import com.weiho.scaffold.system.mapper.MenuMapper;
import com.weiho.scaffold.system.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统菜单表 服务实现类
 * </p>
 *
 * @author Weiho
 * @since 2022-08-04
 */
@Service
@RequiredArgsConstructor
public class MenuServiceImpl extends CommonServiceImpl<MenuMapper, Menu> implements MenuService {
    private final MenuConvert menuConvert;

    @Override
    @Cacheable(value = "Scaffold:Commons:Menus", key = "'loadMenusByUsername:' + #p1")
    public List<MenuDTO> findListByRoles(List<Role> roles, String username) {
        List<Long> roleIds = roles.stream().map(Role::getId).collect(Collectors.toList());
        List<Menu> menus = this.getBaseMapper().findListByRoles(roleIds);
        return menuConvert.toDto(menus);
    }

    @Override
    public List<MenuDTO> buildTree(List<MenuDTO> menuDTOS) {
        //构建空地返回结果
        List<MenuDTO> trees = new ArrayList<>();
        //构造作为子菜单的主键集合,用于防止菜单树为空
        Set<Long> ids = new HashSet<>();
        for (MenuDTO menuDTO : menuDTOS) {
            //判断是否是顶级菜单,是，则直接加入结果集
            if (menuDTO.getParentId() == 0) {
                trees.add(menuDTO);
            }
            //处理不是顶级菜单，再次遍历
            for (MenuDTO it : menuDTOS) {
                //若某一个菜单是某一个顶级菜单的子菜单则放入children中
                if (it.getParentId().equals(menuDTO.getId())) {
                    //children非空判断
                    if (menuDTO.getChildren() == null) {
                        menuDTO.setChildren(new ArrayList<>());
                    }
                    menuDTO.getChildren().add(it);
                    ids.add(it.getId());
                }
            }
        }
        //过滤作为子菜单的menu,将下一级菜单进行展示,保证tree不是空
        if (trees.size() == 0) {
            trees = menuDTOS.stream().filter(s -> !ids.contains(s.getId())).collect(Collectors.toList());
        }
        return trees;
    }

    @Override
    public List<MenuVO> buildMenuList(List<MenuDTO> menuDTOS) {
        //采用链表结构，提高add和remove有效率
        List<MenuVO> menuVOs = new LinkedList<>();
        menuDTOS.forEach(menuDTO -> {
            if (menuDTO != null) {
                //获取子菜单
                List<MenuDTO> menuDTOList = menuDTO.getChildren();
                //构造VO对象
                MenuVO menuVO = new MenuVO();
                //设置菜单名称 (如果组件名不为空则拿组件名，否则拿name)
                menuVO.setName(ObjectUtil.isNotEmpty(menuDTO.getComponentName()) ? menuDTO.getComponentName() : menuDTO.getName());
                //一级目录的path需要加上'/'
                menuVO.setPath(menuDTO.getParentId() == 0 ? "/" + menuDTO.getPath() : menuDTO.getPath());
                menuVO.setHidden(menuDTO.getHidden());

                //如果是顶级菜单，加上Layout标识，给前端处理
                if (menuDTO.getParentId() == 0) {
                    menuVO.setComponent(StrUtil.isEmpty(menuDTO.getComponent()) ? "Layout" : menuVO.getComponent());
                } else if (!StrUtil.isEmpty(menuDTO.getComponent())) {
                    menuVO.setComponent(menuDTO.getComponent());
                }

                //设置Meta对象
                menuVO.setMeta(new MenuMetaVO(menuDTO.getName(), menuDTO.getIconCls()));

                //处理Children
                if (menuDTOList != null && menuDTOList.size() != 0) {
                    menuVO.setRedirect("noRedirect");
                    //递归处理
                    menuVO.setChildren(buildMenuList(menuDTOList));
                } else if (menuDTO.getParentId() == 0) {
                    //当一级菜单没有子菜单
                    //构造一个子菜单
                    MenuVO menuVO1 = new MenuVO();
                    menuVO1.setMeta(menuVO.getMeta());
                    menuVO1.setPath("index");
                    menuVO1.setName(menuVO.getName());
                    menuVO1.setComponent(menuVO.getComponent());
                    menuVO1.setHidden(false);

                    //父菜单
                    menuVO.setName(null);
                    menuVO.setMeta(null);
                    menuVO.setComponent("Layout");
                    List<MenuVO> list = new ArrayList<MenuVO>() {{
                        add(menuVO1);
                    }};
                    menuVO.setChildren(list);
                }

                menuVOs.add(menuVO);
            }
        });
        return menuVOs;
    }
}
