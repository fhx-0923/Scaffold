package com.weiho.scaffold.system.controller;

import com.weiho.scaffold.system.service.AvatarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 用户头像表 前端控制器
 * </p>
 *
 * @author Weiho
 * @since 2022-07-29
 */
@Api(tags = "头像管理")
@RestController
@RequestMapping("/api/v1/avatars")
@RequiredArgsConstructor
public class AvatarController {
    private final AvatarService avatarService;

    @ApiOperation("查询头像列表")
    @GetMapping
    @PreAuthorize("@el.check('Avatar:list')")
    @ApiImplicitParam(name = "usernameLike", value = "用户名模糊查询,赋空值则默认全查",
            dataType = "String", dataTypeClass = String.class)
    public Map<String, Object> getAvatarList(String usernameLike, Pageable pageable) {
        return avatarService.selectAvatarList(usernameLike, pageable);
    }
}
