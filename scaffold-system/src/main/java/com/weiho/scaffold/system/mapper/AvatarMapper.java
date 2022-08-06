package com.weiho.scaffold.system.mapper;

import com.weiho.scaffold.mp.mapper.CommonMapper;
import com.weiho.scaffold.system.entity.Avatar;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户头像表 Mapper 接口
 * </p>
 *
 * @author Weiho
 * @since 2022-07-29
 */
@Mapper
@Repository
public interface AvatarMapper extends CommonMapper<Avatar> {
}
