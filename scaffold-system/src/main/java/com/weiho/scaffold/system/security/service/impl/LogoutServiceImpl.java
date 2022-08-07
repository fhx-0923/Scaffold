package com.weiho.scaffold.system.security.service.impl;

import com.weiho.scaffold.common.util.result.Result;
import com.weiho.scaffold.system.security.service.LogoutService;
import com.weiho.scaffold.system.security.service.OnlineUserService;
import com.weiho.scaffold.system.security.token.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统注销
 *
 * @author Weiho
 * @date 2022/8/5
 */
@Service
public class LogoutServiceImpl implements LogoutService {
    @Autowired
    private OnlineUserService onlineUserService;

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    @CacheEvict(value = "Scaffold:Permission", key = "'loadPermissionByUser:' + @tokenUtils.getUsernameFromToken(@tokenUtils.getTokenFromRequest(#p0))")
    public Result logout(HttpServletRequest request) {
        onlineUserService.logout(tokenUtils.getTokenFromRequest(request));
        return Result.success("注销成功！");
    }
}
