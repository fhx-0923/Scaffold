package com.weiho.scaffold.system.controller;


import com.weiho.scaffold.system.entity.Role;
import com.weiho.scaffold.system.entity.criteria.RoleQueryCriteria;
import com.weiho.scaffold.system.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 系统角色表 前端控制器
 * </p>
 *
 * @author Weiho
 * @since 2022-08-04
 */
@Api(tags = "系统角色接口")
@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    @ApiOperation("获取所有角色列表")
    @PreAuthorize("@el.check('Role:list')")
    public List<Role> getAllRoles(RoleQueryCriteria criteria,
                                  @PageableDefault(value = 2000, sort = {"level"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return roleService.findAll(criteria, pageable);
    }
}
