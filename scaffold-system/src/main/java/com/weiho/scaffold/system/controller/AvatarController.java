package com.weiho.scaffold.system.controller;

import com.weiho.scaffold.common.exception.BadRequestException;
import com.weiho.scaffold.common.util.message.I18nMessagesUtils;
import com.weiho.scaffold.common.util.result.Result;
import com.weiho.scaffold.logging.annotation.Logging;
import com.weiho.scaffold.system.entity.criteria.AvatarQueryCriteria;
import com.weiho.scaffold.system.service.AvatarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
    public Map<String, Object> getAvatarList(AvatarQueryCriteria criteria, Pageable pageable) {
        return avatarService.selectAvatarList(criteria, pageable);
    }

    @Logging(title = "导出头像信息")
    @GetMapping("/download")
    @ApiOperation("导出头像信息")
    @PreAuthorize("@el.check('Avatar:list')")
    public void download(HttpServletResponse response, AvatarQueryCriteria criteria) throws IOException {
        avatarService.download(avatarService.getAll(criteria), response);
    }

    @DeleteMapping
    @Logging(title = "删除头像信息")
    @ApiOperation("删除头像信息")
    @PreAuthorize("@el.check('Avatar:delete')")
    public Result deleteAvatar(@RequestBody Set<Long> ids) {
        // 过滤空值
        ids = ids.stream().filter(Objects::nonNull).collect(Collectors.toSet());
        // 部分用户不存在头像则抛出异常
        if (ids.size() == 0) {
            throw new BadRequestException(I18nMessagesUtils.get("avatar.error.tip"));
        }
        avatarService.delete(ids);
        return Result.success(I18nMessagesUtils.get("delete.success.tip"));
    }

    // TODO 明天完成修改头像信息
}
