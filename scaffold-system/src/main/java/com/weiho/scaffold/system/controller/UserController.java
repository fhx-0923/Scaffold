package com.weiho.scaffold.system.controller;


import com.weiho.scaffold.common.config.system.ScaffoldSystemProperties;
import com.weiho.scaffold.common.exception.BadRequestException;
import com.weiho.scaffold.common.util.message.I18nMessagesUtils;
import com.weiho.scaffold.common.util.result.Result;
import com.weiho.scaffold.common.util.rsa.RsaUtils;
import com.weiho.scaffold.common.util.security.SecurityUtils;
import com.weiho.scaffold.redis.limiter.annotation.RateLimiter;
import com.weiho.scaffold.redis.limiter.enums.LimitType;
import com.weiho.scaffold.system.entity.User;
import com.weiho.scaffold.system.entity.vo.UserPassVO;
import com.weiho.scaffold.system.security.vo.JwtUserVO;
import com.weiho.scaffold.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    private final ScaffoldSystemProperties properties;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @ApiOperation("获取登录后的用户信息")
    @GetMapping("/info")
    @RateLimiter(limitType = LimitType.IP)
    public Result getUserInfo() {
        JwtUserVO jwtUserVO = (JwtUserVO) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        return Result.success(jwtUserVO);
    }

    @ApiOperation("修改密码")
    @PostMapping("/updatePass")
    public Result updatePass(@RequestBody @Validated UserPassVO passVO) throws Exception {
        // 密码解密
        String oldPass = RsaUtils.decryptByPrivateKey(properties.getRsaProperties().getPrivateKey(), passVO.getOldPassword());
        String newPass = RsaUtils.decryptByPrivateKey(properties.getRsaProperties().getPrivateKey(), passVO.getNewPassword());
        User user = userService.findByUsername(SecurityUtils.getUsername());
        if (!passwordEncoder.matches(oldPass, user.getPassword())) {
            throw new BadRequestException(I18nMessagesUtils.get("user.update.oldPassError"));
        }
        if (passwordEncoder.matches(newPass, user.getPassword())) {
            throw new BadRequestException(I18nMessagesUtils.get("user.update.tip"));
        }
        userService.updatePass(user.getPassword(), passwordEncoder.encode(newPass));
        return Result.success(I18nMessagesUtils.get("update.success.tip"));
    }
}
