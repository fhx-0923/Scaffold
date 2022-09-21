package com.weiho.scaffold.system.entity.convert;

import com.weiho.scaffold.common.mapstruct.MapStructConvert;
import com.weiho.scaffold.system.entity.User;
import com.weiho.scaffold.system.entity.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Weiho
 * @since 2022/9/14
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserVOConvert extends MapStructConvert<User, UserVO> {
}
