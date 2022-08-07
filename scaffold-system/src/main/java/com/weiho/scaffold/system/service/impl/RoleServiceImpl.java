package com.weiho.scaffold.system.service.impl;

import com.weiho.scaffold.common.util.string.StringUtils;
import com.weiho.scaffold.mp.service.impl.CommonServiceImpl;
import com.weiho.scaffold.system.entity.Menu;
import com.weiho.scaffold.system.entity.Role;
import com.weiho.scaffold.system.entity.convert.RoleConvert;
import com.weiho.scaffold.system.entity.dto.RoleDTO;
import com.weiho.scaffold.system.mapper.MenuMapper;
import com.weiho.scaffold.system.mapper.RoleMapper;
import com.weiho.scaffold.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
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
public class RoleServiceImpl extends CommonServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleConvert roleConvert;

    @Override
    @Cacheable(value = "Scaffold:Permission", key = "'loadPermissionByUser:' + @userMapper.selectById(#p0).getUsername()", unless = "#result.size() <= 1")
    public Collection<SimpleGrantedAuthority> mapToGrantedAuthorities(Long userId) {
        //获取用户角色集合
        Set<Role> roles = this.getBaseMapper().findByUserId(userId);
        Set<RoleDTO> roleDTOS = new HashSet<>();
        for (Role role : roles) {
            //转化DTO对象
            RoleDTO roleDTO = roleConvert.toDto(role);
            //根据角色ID获取menu集合
            Set<Menu> menuSet = menuMapper.findMenusByRoleId(role.getId());
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
}
