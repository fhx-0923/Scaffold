package com.weiho.scaffold.system.controller;


import com.weiho.scaffold.common.annotation.RateLimiter;
import com.weiho.scaffold.common.limiting.enums.LimitType;
import com.weiho.scaffold.common.util.result.Result;
import com.weiho.scaffold.common.util.security.SecurityUtils;
import com.weiho.scaffold.system.security.vo.JwtUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @author Weiho
 * @since 2022-08-04
 */
@RestController
@RequestMapping("/api/user")
@Api(tags = "系统用户接口")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserDetailsService userDetailsService;

    @ApiOperation("获取登录后的用户信息")
    @GetMapping("/info")
    @RateLimiter(limitType = LimitType.IP)
    public Result getUserInfo() {
        JwtUserVO jwtUserVO = (JwtUserVO) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        return Result.success(jwtUserVO);
    }
}
