package com.weiho.scaffold.logging.entity.convert;

import com.weiho.scaffold.common.mapstruct.MapStructConvert;
import com.weiho.scaffold.logging.entity.Log;
import com.weiho.scaffold.logging.entity.vo.LogErrorUserVO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Weiho
 * @date 2022/9/13
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LogErrorUserVOConvert extends MapStructConvert<Log, LogErrorUserVO> {
}
