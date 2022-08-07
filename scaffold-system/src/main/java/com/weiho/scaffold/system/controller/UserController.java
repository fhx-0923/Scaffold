package com.weiho.scaffold.system.controller;


import com.weiho.scaffold.common.util.result.Result;
import com.weiho.scaffold.common.util.security.SecurityUtils;
import com.weiho.scaffold.system.security.vo.JwtUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserController {
    @Autowired
    private UserDetailsService userDetailsService;

    @ApiOperation("获取登录后的用户信息")
    @GetMapping("/info")
    public Result getUserInfo() {
        JwtUserVO jwtUserVO = (JwtUserVO) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        return Result.success(jwtUserVO);
    }
}
