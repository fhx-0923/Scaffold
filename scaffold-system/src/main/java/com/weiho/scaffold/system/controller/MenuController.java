package com.weiho.scaffold.system.controller;

import com.weiho.scaffold.common.exception.SecurityException;
import com.weiho.scaffold.common.util.message.I18nMessagesUtils;
import com.weiho.scaffold.common.util.result.enums.ResultCodeEnum;
import com.weiho.scaffold.common.util.security.SecurityUtils;
import com.weiho.scaffold.system.entity.User;
import com.weiho.scaffold.system.entity.dto.MenuDTO;
import com.weiho.scaffold.system.entity.vo.MenuVO;
import com.weiho.scaffold.system.service.MenuService;
import com.weiho.scaffold.system.service.RoleService;
import com.weiho.scaffold.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/api/v1/menus")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;
    private final RoleService roleService;
    private final UserService userService;

    @ApiOperation("获取前端所需菜单")
    @GetMapping("/build")
    public List<MenuVO> buildMenuList(HttpServletRequest request) {
        try {
            String username = SecurityUtils.getUsername();
            //获取当前登录的用户的ID
            User user = userService.findByUsername(username);
            //获取能访问的菜单列表
            List<MenuDTO> menuDTOList = menuService.findListByRoles(roleService.findListByUser(user), username);
            //构建菜单树
            List<MenuDTO> menuDTOTree = menuService.buildTree(menuDTOList);
            //递归转成前端所需的结构,返回
            return menuService.buildMenuList(menuDTOTree, request);
        } catch (Exception e) {
            throw new SecurityException(ResultCodeEnum.FAILED, I18nMessagesUtils.get("menu.error.tip"));
        }
    }

    @ApiOperation("获取菜单树")
    @GetMapping("/tree")
    @PreAuthorize("@el.check('Role:list','Menu:list')")
    public Object getMenuTree(HttpServletRequest request) {
        return menuService.getMenuTree(menuService.findByPid(0L), request);
    }
}
