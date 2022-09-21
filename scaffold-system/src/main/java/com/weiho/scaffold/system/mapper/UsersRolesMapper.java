package com.weiho.scaffold.system.mapper;

import com.weiho.scaffold.system.entity.UsersRoles;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户角色中间表 Mapper 接口
 * </p>
 *
 * @author Weiho
 * @since 2022-09-21
 */
@Mapper
@Repository
public interface UsersRolesMapper {
    /**
     * 根据用户名删除所有的角色
     *
     * @param userId 用户ID
     */
    void deleteRolesByUserId(@Param("userId") Long userId);

    /**
     * 为用户添加角色
     *
     * @param usersRoles 实体
     */
    void save(UsersRoles usersRoles);
}
