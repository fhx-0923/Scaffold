package com.weiho.scaffold.system.service;

import com.weiho.scaffold.mp.service.CommonService;
import com.weiho.scaffold.system.entity.Role;
import com.weiho.scaffold.system.entity.User;
import com.weiho.scaffold.system.entity.criteria.RoleQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 系统角色表 服务类
 * </p>
 *
 * @author Weiho
 * @since 2022-08-04
 */
public interface RoleService extends CommonService<Role> {
    /**
     * 根据用户ID获取用户的所有权限
     *
     * @param userId   用户ID
     * @param username 当前登录的用户名
     * @return 权限集合
     */
    Collection<SimpleGrantedAuthority> mapToGrantedAuthorities(Long userId, String username);

    /**
     * 根据用户ID查询所有的角色信息
     *
     * @param user 用户实体
     * @return 角色集合
     */
    List<Role> findListByUser(User user);

    /**
     * 查询所有的角色列表
     *
     * @param criteria 查询条件
     * @param pageable 分页条件
     * @return /
     */
    List<Role> findAll(RoleQueryCriteria criteria, Pageable pageable);

    /**
     * 根据用户ID查找该用户的最高角色等级
     *
     * @param userId 用户ID
     * @return 角色等级
     */
    Integer findHighLevel(Long userId);

    /**
     * 检查当前传入的用户ID的角色等级是否低于当前操作的用户
     * 若低于当前操作的用户则抛出异常
     *
     * @param resourceId 检查当前的用户ID
     */
    void checkLevel(Long resourceId);

    /**
     * 检查当前传入的角色等级是否低于当前操作的用户
     * 若低于当前操作的用户则抛出异常
     *
     * @param roles 角色集合
     */
    void checkLevel(Set<Role> roles);
}
