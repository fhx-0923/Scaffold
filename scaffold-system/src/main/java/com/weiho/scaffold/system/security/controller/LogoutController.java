package com.weiho.scaffold.system.security.controller;

import com.weiho.scaffold.common.annotation.Anonymous;
import com.weiho.scaffold.common.util.result.Result;
import com.weiho.scaffold.logging.annotation.Logging;
import com.weiho.scaffold.logging.enums.BusinessTypeEnum;
import com.weiho.scaffold.system.security.service.LogoutService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统注销
 *
 * @author Weiho
 */
@Api(tags = "系统注销")
@RestController
@RequestMapping("/api/out")
@RequiredArgsConstructor
public class LogoutController {
    private final LogoutService logoutService;

    @Anonymous
    @ApiOperation("注销登录")
    @Logging(title = "注销登录", businessType = BusinessTypeEnum.DELETE)
    @DeleteMapping("/logout")
    public Result logout(HttpServletRequest request) {
        return logoutService.logout(request);
    }
}
