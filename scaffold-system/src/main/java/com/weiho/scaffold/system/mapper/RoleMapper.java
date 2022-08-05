package com.weiho.scaffold.system.mapper;

import com.weiho.scaffold.mp.mapper.CommonMapper;
import com.weiho.scaffold.system.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * <p>
 * 系统角色表 Mapper 接口
 * </p>
 *
 * @author Weiho
 * @since 2022-08-04
 */
@Mapper
@Repository
public interface RoleMapper extends CommonMapper<Role> {
    /**
     * 根据用户ID查找用户的角色集合
     *
     * @param userId 用户ID
     * @return 角色集合
     */
    Set<Role> findByUserId(@Param("userId") Long userId);
}
