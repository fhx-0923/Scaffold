package com.weiho.scaffold.logging.entity.convert;

import com.weiho.scaffold.common.mapstruct.MapStructConvert;
import com.weiho.scaffold.logging.entity.Log;
import com.weiho.scaffold.logging.entity.vo.LogErrorVO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Weiho
 * @since 2022/8/29
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LogErrorVOConvert extends MapStructConvert<Log, LogErrorVO> {
}
