package com.weiho.scaffold.system.service.impl;

import com.weiho.scaffold.mp.service.impl.CommonServiceImpl;
import com.weiho.scaffold.system.entity.Menu;
import com.weiho.scaffold.system.mapper.MenuMapper;
import com.weiho.scaffold.system.service.MenuService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统菜单表 服务实现类
 * </p>
 *
 * @author Weiho
 * @since 2022-08-04
 */
@Service
public class MenuServiceImpl extends CommonServiceImpl<MenuMapper, Menu> implements MenuService {

}
