package com.weiho.scaffold.system.mapper;

import com.weiho.scaffold.mp.mapper.CommonMapper;
import com.weiho.scaffold.system.entity.SysSetting;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 系统参数表 Mapper 接口
 * </p>
 *
 * @author Weiho
 * @since 2022-09-19
 */
@Mapper
@Repository
public interface SysSettingMapper extends CommonMapper<SysSetting> {
}
