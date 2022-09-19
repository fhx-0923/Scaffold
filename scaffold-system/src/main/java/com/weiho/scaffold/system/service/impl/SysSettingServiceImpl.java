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
    public SysSettingVO getSysSettings() {
        return sysSettingConvert.toPojo(this.list().get(0));
    }
}
