package com.weiho.scaffold.system.service.impl;

import com.weiho.scaffold.mp.service.impl.CommonServiceImpl;
import com.weiho.scaffold.system.entity.User;
import com.weiho.scaffold.system.entity.convert.UserConvert;
import com.weiho.scaffold.system.mapper.AvatarMapper;
import com.weiho.scaffold.system.mapper.UserMapper;
import com.weiho.scaffold.system.security.vo.JwtUserVO;
import com.weiho.scaffold.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author Weiho
 * @since 2022-08-04
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends CommonServiceImpl<UserMapper, User> implements UserService {
    private final AvatarMapper avatarMapper;
    private final UserConvert userConvert;

    @Override
    public JwtUserVO getBaseJwtUserVO(String username) {
        //查询User
        User user = this.getBaseMapper().findByUsername(username);
        //转化对象
        JwtUserVO jwtUserVO = userConvert.toDto(user);
        //放入头像对象
        jwtUserVO.setAvatar(avatarMapper.selectById(user.getAvatarId()));
        return jwtUserVO;
    }

    @Override
    public User findByUsername(String username) {
        return this.getBaseMapper().findByUsername(username);
    }
}
