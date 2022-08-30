package com.weiho.scaffold.system.entity.convert;

import com.weiho.scaffold.common.mapstruct.MapStructConvert;
import com.weiho.scaffold.system.entity.Role;
import com.weiho.scaffold.system.entity.dto.RoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Weiho
 * @date 2022/8/4
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleDTOConvert extends MapStructConvert<Role, RoleDTO> {
}
