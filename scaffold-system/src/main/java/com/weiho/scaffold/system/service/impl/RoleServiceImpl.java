package com.weiho.scaffold.system.service.impl;

import com.weiho.scaffold.common.exception.SecurityException;
import com.weiho.scaffold.common.util.message.I18nMessagesUtils;
import com.weiho.scaffold.common.util.result.enums.ResultCodeEnum;
import com.weiho.scaffold.common.util.security.SecurityUtils;
import com.weiho.scaffold.common.util.string.StringUtils;
import com.weiho.scaffold.mp.core.QueryHelper;
import com.weiho.scaffold.mp.enums.SortTypeEnum;
import com.weiho.scaffold.mp.service.impl.CommonServiceImpl;
import com.weiho.scaffold.system.entity.Menu;
import com.weiho.scaffold.system.entity.Role;
import com.weiho.scaffold.system.entity.User;
import com.weiho.scaffold.system.entity.convert.RoleDTOConvert;
import com.weiho.scaffold.system.entity.criteria.RoleQueryCriteria;
import com.weiho.scaffold.system.entity.dto.RoleDTO;
import com.weiho.scaffold.system.mapper.MenuMapper;
import com.weiho.scaffold.system.mapper.RoleMapper;
import com.weiho.scaffold.system.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.CastUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author Weiho
 * @since 2022-08-04
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RoleServiceImpl extends CommonServiceImpl<RoleMapper, Role> implements RoleService {
    private final MenuMapper menuMapper;
    private final RoleDTOConvert roleDTOConvert;

    @Override
    @Cacheable(value = "Scaffold:Commons:Permission", key = "'loadPermissionByUsername:' + #p1", unless = "#result.size() <= 1")
    public Collection<SimpleGrantedAuthority> mapToGrantedAuthorities(Long userId, String username) {
        //获取用户角色集合
        Set<Role> roles = this.getBaseMapper().findSetByUserId(userId);
        Set<RoleDTO> roleDTOS = new HashSet<>();
        for (Role role : roles) {
            //转化DTO对象
            RoleDTO roleDTO = roleDTOConvert.toPojo(role);
            //根据角色ID获取menu集合
            Set<Menu> menuSet = menuMapper.findSetByRoleId(role.getId());
            //放入DTO对象
            roleDTO.setMenus(menuSet);
            //加入DTO对象集合
            roleDTOS.add(roleDTO);
        }
        //新建权限集合，将DTO对象集合中的permission抽出来作为新的集合
        Set<String> permissions = roleDTOS.stream().map(RoleDTO::getPermission).filter(StringUtils::isNotBlank).collect(Collectors.toSet());
        //将menu中的权限permission添加进去
        permissions.addAll(
                roleDTOS.stream().flatMap(roleDTO -> roleDTO.getMenus().stream())
                        .map(Menu::getPermission)
                        .filter(StringUtils::isNotBlank).collect(Collectors.toSet())
        );
        return permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    @CachePut(value = "Scaffold:Commons:Permission", key = "'loadPermissionByUsername:' + #p1", unless = "#result.size() <= 1")
    public Collection<SimpleGrantedAuthority> updateCacheForGrantedAuthorities(Long userId, String username) {
        return this.mapToGrantedAuthorities(userId, username);
    }


    @Override
    @Cacheable(value = "Scaffold:Commons:Roles", key = "'loadRolesByUsername:' + #p0.getUsername()")
    public List<Role> findListByUser(User user) {
        return this.getBaseMapper().findListByUserId(user.getId());
    }

    @Override
    @CachePut(value = "Scaffold:Commons:Roles", key = "'loadRolesByUsername:' + #p0.getUsername()")
    public List<Role> updateCacheForRoleList(User user) {
        return this.findListByUser(user);
    }

    @Override
    public List<Role> findAll(RoleQueryCriteria criteria, Pageable pageable) {
        startPage(pageable, "level", SortTypeEnum.ASC);
        return this.getBaseMapper().selectList(CastUtils.cast(QueryHelper.getQueryWrapper(Role.class, criteria)));
    }

    @Override
    public Integer findHighLevel(Long userId) {
        return Collections.min(this.getBaseMapper().findListByUserId(userId).stream().map(Role::getLevel).collect(Collectors.toList()));
    }

    @Override
    public void checkLevel(Long resourceId) {
        Integer resourceUserLevel = this.findHighLevel(resourceId);
        Integer securityUserLevel = this.findHighLevel(SecurityUtils.getUserId());
        if (resourceUserLevel < securityUserLevel) {
            throw new SecurityException(ResultCodeEnum.FAILED, I18nMessagesUtils.get("permission.none.tip"));
        }
    }

    @Override
    public void checkLevel(Set<Role> roles) {
        Set<Integer> integers = new HashSet<>();
        Set<Long> ids = roles.stream().map(Role::getId).collect(Collectors.toSet());
        for (Long id : ids) {
            integers.add(this.getBaseMapper().selectById(id).getLevel());
        }
        Integer securityUserLevel = this.findHighLevel(SecurityUtils.getUserId());
        Integer rolesUserLevel = Collections.min(integers);
        if (rolesUserLevel < securityUserLevel) {
            throw new SecurityException(ResultCodeEnum.FAILED, I18nMessagesUtils.get("permission.none.tip"));
        }
    }
}
