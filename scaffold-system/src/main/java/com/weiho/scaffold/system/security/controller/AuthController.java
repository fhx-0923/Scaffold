package com.weiho.scaffold.system.security.controller;

import com.weiho.scaffold.common.annotation.Anonymous;
import com.weiho.scaffold.common.annotation.RateLimiter;
import com.weiho.scaffold.common.limiting.enums.LimitType;
import com.weiho.scaffold.logging.annotation.Logging;
import com.weiho.scaffold.system.security.service.LoginService;
import com.weiho.scaffold.system.security.vo.AuthUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth")
@Api(tags = "系统授权")
public class AuthController {
    @Autowired
    private LoginService loginService;

    @Anonymous
    @ApiOperation("登录授权接口")
    @Logging(title = "用户登录")
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody @Validated AuthUserVO authUserVO, HttpServletRequest request) {
        return loginService.login(authUserVO, request);
    }

    @Anonymous
    @ApiOperation("获取验证码")
    @GetMapping(value = "/verifyCode")
    @RateLimiter(limitType = LimitType.IP)
    public Map<String, Object> getVerifyCode() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        //获取验证码信息并打包成Json
        return loginService.getVerifyCodeInfo();
    }
}
