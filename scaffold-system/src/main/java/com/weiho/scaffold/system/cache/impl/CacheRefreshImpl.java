package com.weiho.scaffold.system.cache.impl;

import com.weiho.scaffold.system.cache.service.CacheRefresh;
import com.weiho.scaffold.system.entity.Role;
import com.weiho.scaffold.system.entity.User;
import com.weiho.scaffold.system.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @author Weiho
 * @since 2022/9/26
 */
@Service
@RequiredArgsConstructor
public class CacheRefreshImpl implements CacheRefresh {
    private final RoleService roleService;

    @Override
    @CachePut(value = "Scaffold:Commons:User", key = "'loadUserByUsername:' + #p0.getUsername()", unless = "#result == null || #result.enabled == false")
    public User updateUserCache(User newUser) {
        return newUser;
    }

    @Override
    @CachePut(value = "Scaffold:Commons:Permission", key = "'loadPermissionByUsername:' + #p1", unless = "#result.size() <= 1")
    public Collection<SimpleGrantedAuthority> updateRolesCacheForGrantedAuthorities(Long userId, String username) {
        return roleService.mapToGrantedAuthorities(userId, username);
    }

    @Override
    @CachePut(value = "Scaffold:Commons:Roles", key = "'loadRolesByUsername:' + #p0.getUsername()")
    public List<Role> updateRolesCacheForRoleList(User user) {
        return roleService.findListByUser(user);
    }
}
