package com.weiho.scaffold.system.service;

import com.weiho.scaffold.mp.service.CommonService;
import com.weiho.scaffold.system.entity.SysSetting;
import com.weiho.scaffold.system.entity.vo.SysSettingVO;

/**
 * <p>
 * 系统参数表 服务类
 * </p>
 *
 * @author Weiho
 * @since 2022-09-19
 */
public interface SysSettingService extends CommonService<SysSetting> {
    SysSettingVO getSysSettings();
}
