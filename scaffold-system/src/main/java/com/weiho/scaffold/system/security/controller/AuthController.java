package com.weiho.scaffold.system.security.controller;

import com.weiho.scaffold.common.annotation.Anonymous;
import com.weiho.scaffold.system.security.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth")
@Api(tags = "系统授权")
public class AuthController {
    @Autowired
    private LoginService loginService;


    @Anonymous
    @ApiOperation("获取验证码")
    @GetMapping(value = "/verifyCode")
    public Map<String, Object> getVerifyCode() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        //获取验证码信息并打包成Json
        return loginService.getVerifyCodeInfo();
    }
}
