package com.weiho.scaffold.system.service;

import com.weiho.scaffold.mp.service.CommonService;
import com.weiho.scaffold.system.entity.Role;
import com.weiho.scaffold.system.entity.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

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
}
