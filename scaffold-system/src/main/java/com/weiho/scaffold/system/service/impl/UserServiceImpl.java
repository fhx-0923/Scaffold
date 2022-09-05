package com.weiho.scaffold.system.service.impl;

import com.weiho.scaffold.common.util.date.DateUtils;
import com.weiho.scaffold.common.util.date.FormatEnum;
import com.weiho.scaffold.mp.service.impl.CommonServiceImpl;
import com.weiho.scaffold.system.entity.User;
import com.weiho.scaffold.system.entity.convert.JwtUserVOConvert;
import com.weiho.scaffold.system.mapper.UserMapper;
import com.weiho.scaffold.system.security.vo.JwtUserVO;
import com.weiho.scaffold.system.service.AvatarService;
import com.weiho.scaffold.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
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
    private final AvatarService avatarService;
    private final JwtUserVOConvert jwtUserVOConvert;

    @Override
    public JwtUserVO getBaseJwtUserVO(User user) {
        //转化对象
        JwtUserVO jwtUserVO = jwtUserVOConvert.toPojo(user);
        //放入头像对象
        jwtUserVO.setAvatar(avatarService.selectByAvatarId(user.getAvatarId(), user.getUsername()));
        return jwtUserVO;
    }

    @Override
    @Cacheable(value = "Scaffold:Commons:User", key = "'loadUserByUsername:' + #p0", unless = "#result == null || #result.enabled == false")
    public User findByUsername(String username) {
        try {
            return this.getBaseMapper().findByUsername(username);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void updatePass(String username, String encryptPassword) {
        this.getBaseMapper().updatePass(username, encryptPassword, DateUtils.getNowDateFormat(FormatEnum.YYYY_MM_DD_HH_MM_SS));
    }
}
