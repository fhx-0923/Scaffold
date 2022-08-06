package com.weiho.scaffold.system.service.impl;

import com.weiho.scaffold.mp.service.impl.CommonServiceImpl;
import com.weiho.scaffold.system.entity.Avatar;
import com.weiho.scaffold.system.mapper.AvatarMapper;
import com.weiho.scaffold.system.service.AvatarService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户头像表 服务实现类
 * </p>
 *
 * @author Weiho
 * @since 2022-07-29
 */
@Service
public class AvatarServiceImpl extends CommonServiceImpl<AvatarMapper, Avatar> implements AvatarService {
}
