package com.weiho.scaffold.system.service;

import com.weiho.scaffold.mp.service.CommonService;
import com.weiho.scaffold.system.entity.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

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
     * @param userId 用户ID
     * @return 权限集合
     */
    Collection<SimpleGrantedAuthority> mapToGrantedAuthorities(Long userId);
}
