package com.weiho.scaffold.system.entity.convert;

import com.weiho.scaffold.common.mapstruct.MapStructConvert;
import com.weiho.scaffold.system.entity.Role;
import com.weiho.scaffold.system.entity.vo.RoleVO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Weiho
 * @since 2022/9/28
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleVOConvert extends MapStructConvert<Role, RoleVO> {
}
