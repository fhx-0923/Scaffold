package com.weiho.scaffold.logging.mapper;

import com.weiho.scaffold.logging.entity.Log;
import com.weiho.scaffold.mp.mapper.CommonMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Weiho
 * @since 2022-08-06
 */
@Mapper
@Repository
public interface LogMapper extends CommonMapper<Log> {
    /**
     * 根据日志的类型删除日志
     *
     * @param logType 日志的类型(INFO ERROR)
     */
    void deleteByLogType(@Param("logType") String logType);
}
