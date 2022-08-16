package com.weiho.scaffold.system.controller;

import com.weiho.scaffold.common.util.security.SecurityUtils;
import com.weiho.scaffold.system.entity.dto.MenuDTO;
import com.weiho.scaffold.system.entity.vo.MenuVO;
import com.weiho.scaffold.system.service.MenuService;
import com.weiho.scaffold.system.service.RoleService;
import com.weiho.scaffold.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 系统菜单表 前端控制器
 * </p>
 *
 * @author Weiho
 * @since 2022-08-04
 */
@Api(tags = "菜单管理")
@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;
    private final RoleService roleService;
    private final UserService userService;

    @ApiOperation("获取前端所需菜单")
    @GetMapping("/build")
    public List<MenuVO> buildMenuList() {
        //获取当前登录的用户的ID
        Long userId = userService.findByUsername(SecurityUtils.getUsername()).getId();
        //获取能访问的菜单列表
        List<MenuDTO> menuDTOList = menuService.findListByRoles(roleService.findListByUserId(userId));
        //构建菜单树
        List<MenuDTO> menuDTOTree = menuService.buildTree(menuDTOList);
        //递归转成前端所需的结构,返回
        return menuService.buildMenuList(menuDTOTree);
    }
}
