package com.weiho.scaffold.system.controller;


import com.weiho.scaffold.redis.limiter.annotation.RateLimiter;
import com.weiho.scaffold.redis.limiter.enums.LimitType;
import com.weiho.scaffold.system.entity.vo.SysSettingVO;
import com.weiho.scaffold.system.service.SysSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 系统参数表 前端控制器
 * </p>
 *
 * @author Weiho
 * @since 2022-09-19
 */
@Api(tags = "系统参数管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/settings")
public class SysSettingController {
    private final SysSettingService sysSettingService;

    @ApiOperation("获取系统参数")
    @GetMapping
    @RateLimiter(limitType = LimitType.IP)
    public Map<String, Object> getSysSettings(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        SysSettingVO sysSettingVO = sysSettingService.getSysSettings();
        result.put("logo", sysSettingVO.getSysLogo());
        String language = request.getHeader("Accept-Language") == null ? "zh-CN" : request.getHeader("Accept-Language");
        switch (language) {
            case "zh-CN":
                result.put("title", sysSettingVO.getSysNameZhCn());
                break;
            case "zh-HK":
                result.put("title", sysSettingVO.getSysNameZhHk());
                break;
            case "zh-TW":
                result.put("title", sysSettingVO.getSysNameZhTw());
                break;
            case "en-US":
                result.put("title", sysSettingVO.getSysNameEnUs());
                break;
            default:
                result.put("title", sysSettingVO.getSysName());
                break;
        }
        return result;
    }
}
