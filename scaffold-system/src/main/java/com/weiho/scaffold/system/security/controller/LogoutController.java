package com.weiho.scaffold.system.security.controller;

import com.weiho.scaffold.common.annotation.Anonymous;
import com.weiho.scaffold.common.util.result.Result;
import com.weiho.scaffold.system.security.service.LogoutService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统注销
 *
 * @author Weiho
 * @date 2022/8/5
 */
@Api(tags = "系统注销")
@RestController
@RequestMapping("/out")
public class LogoutController {
    @Autowired
    private LogoutService logoutService;

    @Anonymous
    @ApiOperation("注销登录")
    @DeleteMapping("/logout")
    public Result logout(HttpServletRequest request) {
        return logoutService.logout(request);
    }
}
