package com.weiho.scaffold.system.controller;

import com.weiho.scaffold.common.exception.BadRequestException;
import com.weiho.scaffold.common.exception.SecurityException;
import com.weiho.scaffold.common.util.message.I18nMessagesUtils;
import com.weiho.scaffold.common.util.result.Result;
import com.weiho.scaffold.common.util.result.enums.ResultCodeEnum;
import com.weiho.scaffold.common.util.security.SecurityUtils;
import com.weiho.scaffold.logging.annotation.Logging;
import com.weiho.scaffold.system.entity.Menu;
import com.weiho.scaffold.system.entity.User;
import com.weiho.scaffold.system.entity.convert.MenuDTOConvert;
import com.weiho.scaffold.system.entity.criteria.MenuQueryCriteria;
import com.weiho.scaffold.system.entity.dto.MenuDTO;
import com.weiho.scaffold.system.entity.vo.MenuVO;
import com.weiho.scaffold.system.service.MenuService;
import com.weiho.scaffold.system.service.RoleService;
import com.weiho.scaffold.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private final MenuDTOConvert menuDTOConvert;
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
        return menuService.getMenuTree(menuService.findByPid(0L).stream().filter(Menu::isEnabled).collect(Collectors.toList()), request);
    }

    @ApiOperation("查询菜单列表")
    @GetMapping
    @PreAuthorize("@el.check('Menu:list')")
    public Map<String, Object> getMenuList(MenuQueryCriteria criteria) {
        List<MenuDTO> menuDTOS = menuDTOConvert.toPojo(menuService.getAll(criteria));
        return menuService.buildTreeForList(menuDTOS);
    }

    @Logging(title = "导出菜单数据")
    @ApiOperation("导出菜单数据")
    @GetMapping("/download")
    @PreAuthorize("@el.check('Menu:list')")
    public void download(HttpServletResponse response, MenuQueryCriteria criteria) throws IOException {
        menuService.download(menuDTOConvert.toPojo(menuService.getAll(criteria)), response);
    }

    @Logging(title = "新增菜单")
    @ApiOperation("新增菜单")
    @PostMapping
    @PreAuthorize("@el.check('Menu:add')")
    public Result addMenu(@Validated @RequestBody Menu resources) {
        if (resources.getId() != null) {
            throw new BadRequestException("新增的菜单不能拥有ID");
        }
        menuService.create(resources);
        return Result.success(I18nMessagesUtils.get("add.success.tip"));
    }
}
