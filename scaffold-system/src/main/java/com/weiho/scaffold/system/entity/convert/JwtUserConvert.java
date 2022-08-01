package com.weiho.scaffold.system.entity.convert;

import com.weiho.scaffold.common.mapstruct.MapStructConvert;
import com.weiho.scaffold.system.entity.User;
import com.weiho.scaffold.system.security.vo.JwtUserVO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * User转JwtUser
 *
 * @author Weiho
 * @date 2022/7/29
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JwtUserConvert extends MapStructConvert<User, JwtUserVO> {
}
