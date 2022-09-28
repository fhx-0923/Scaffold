package com.weiho.scaffold.system.controller;

import com.weiho.scaffold.common.util.message.I18nMessagesUtils;
import com.weiho.scaffold.common.util.result.Result;
import com.weiho.scaffold.logging.annotation.Logging;
import com.weiho.scaffold.redis.limiter.annotation.RateLimiter;
import com.weiho.scaffold.redis.limiter.enums.LimitType;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

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

    @GetMapping("/select")
    @ApiOperation("获取所有角色列表(带国际化)")
    @PreAuthorize("@el.check('Role:list')")
    public Map<String, Object> getAllRoles(RoleQueryCriteria criteria, HttpServletRequest request,
                                           @PageableDefault(value = 2000, sort = {"level"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return roleService.findAll(criteria, pageable, request);
    }

    @GetMapping
    @ApiOperation("获取所有角色列表(不带国际化)")
    @PreAuthorize("@el.check('Role:list')")
    public Map<String, Object> getAll(RoleQueryCriteria criteria,
                                      @PageableDefault(value = 2000, sort = {"level"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return roleService.findAll(criteria, pageable);
    }

    @GetMapping("/levelScope")
    @ApiOperation("获取角色权限等级范围")
    @RateLimiter(limitType = LimitType.IP)
    public Map<String, Integer> getLevelScope() {
        return roleService.getLevelScope();
    }

    @Logging(title = "导出角色数据")
    @ApiOperation("导出角色数据")
    @GetMapping("/download")
    @PreAuthorize("@el.check('Role:list')")
    public void download(RoleQueryCriteria criteria, HttpServletRequest request, HttpServletResponse response) throws IOException {
        roleService.download(roleService.findAllForLanguage(criteria, request), response);
    }

    @Logging(title = "修改角色信息")
    @ApiOperation("修改角色信息")
    @PutMapping
    @PreAuthorize("@el.check('Role:update')")
    public Result update(@Validated @RequestBody Role resource) {
        roleService.checkLevel(resource.getLevel());
        roleService.update(resource);
        return Result.success(I18nMessagesUtils.get("update.success.tip"));
    }

    @Logging(title = "删除角色信息")
    @ApiOperation("删除角色信息")
    @DeleteMapping
    @PreAuthorize("@el.check('Role:delete')")
    public Result delete(@RequestBody Set<Long> ids) {
        for (Long id : ids) {
            roleService.checkLevel(roleService.getById(id).getLevel());
        }
        roleService.delete(ids);
        return Result.success(I18nMessagesUtils.get("delete.success.tip"));
    }
    
    // TODO 明天完成菜单修改
}
