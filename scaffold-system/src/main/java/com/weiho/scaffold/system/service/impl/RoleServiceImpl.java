package com.weiho.scaffold.system.service.impl;

import com.weiho.scaffold.system.entity.Role;
import com.weiho.scaffold.system.mapper.RoleMapper;
import com.weiho.scaffold.system.service.RoleService;
import com.weiho.scaffold.mp.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author Weiho
 * @since 2022-07-19
 */
@Service
public class RoleServiceImpl extends CommonServiceImpl<RoleMapper, Role> implements RoleService {
}
