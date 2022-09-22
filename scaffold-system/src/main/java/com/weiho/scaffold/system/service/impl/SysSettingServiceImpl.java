package com.weiho.scaffold.system.service.impl;

import com.weiho.scaffold.mp.service.impl.CommonServiceImpl;
import com.weiho.scaffold.system.entity.SysSetting;
import com.weiho.scaffold.system.entity.convert.SysSettingConvert;
import com.weiho.scaffold.system.entity.vo.SysSettingVO;
import com.weiho.scaffold.system.mapper.SysSettingMapper;
import com.weiho.scaffold.system.service.SysSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 系统参数表 服务实现类
 * </p>
 *
 * @author Weiho
 * @since 2022-09-19
 */
@Service
@RequiredArgsConstructor
public class SysSettingServiceImpl extends CommonServiceImpl<SysSettingMapper, SysSetting> implements SysSettingService {
    private final SysSettingConvert sysSettingConvert;

    @Override
    @Cacheable(value = "Scaffold:System", key = "'settings'")
    public SysSetting getSysSettings() {
        return this.list().get(0);
    }

    @Override
    public Map<String, Object> getLogoAndTitle(HttpServletRequest request, SysSetting sysSetting) {
        Map<String, Object> result = new HashMap<>();
        SysSettingVO sysSettingVO = sysSettingConvert.toPojo(sysSetting);
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
