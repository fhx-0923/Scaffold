package com.weiho.scaffold.system.service;

import com.weiho.scaffold.mp.service.CommonService;
import com.weiho.scaffold.system.entity.Menu;
import com.weiho.scaffold.system.entity.Role;
import com.weiho.scaffold.system.entity.dto.MenuDTO;
import com.weiho.scaffold.system.entity.vo.MenuVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 系统菜单表 服务类
 * </p>
 *
 * @author Weiho
 * @since 2022-08-04
 */
public interface MenuService extends CommonService<Menu> {
    /**
     * 根据角色ID列表查询所有能访问的菜单
     *
     * @param roles 角色列表
     * @return Menu列表
     */
    List<MenuDTO> findListByRoles(List<Role> roles, String username);

    /**
     * 根据菜单列表和父子关系构建菜单树
     *
     * @param menuDTOS 菜单列表
     * @return 菜单树
     */
    List<MenuDTO> buildTree(List<MenuDTO> menuDTOS);

    /**
     * 构建前端所需的菜单json
     *
     * @param menuDTOS 菜单树
     * @return 前端所需的路由表json
     */
    List<MenuVO> buildMenuList(List<MenuDTO> menuDTOS, HttpServletRequest request);

    /**
     * 获取菜单树
     *
     * @param menus   菜单列表
     * @param request 请求对象
     * @return /
     */
    Object getMenuTree(List<Menu> menus, HttpServletRequest request);

    /**
     * 根据上级ID查找菜单列表
     *
     * @param pid 上级ID
     * @return /
     */
    List<Menu> findByPid(long pid);

    /**
     * 根据角色ID查询能访问的所有菜单
     *
     * @param roleId 角色ID
     * @return 菜单集合
     */
    Set<Menu> findSetByRoleId(Long roleId);
}
