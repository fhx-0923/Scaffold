package com.weiho.scaffold.system.mapper;

import com.weiho.scaffold.system.entity.Role;
import com.weiho.scaffold.mp.mapper.CommonMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 系统角色表 Mapper 接口
 * </p>
 *
 * @author Weiho
 * @since 2022-07-19
 */
@Mapper
@Repository
public interface RoleMapper extends CommonMapper<Role> {
}
