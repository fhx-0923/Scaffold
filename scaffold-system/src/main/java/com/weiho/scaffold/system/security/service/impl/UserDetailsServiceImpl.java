package com.weiho.scaffold.system.security.service.impl;

import com.weiho.scaffold.common.exception.BadRequestException;
import com.weiho.scaffold.common.util.date.DateUtils;
import com.weiho.scaffold.system.entity.User;
import com.weiho.scaffold.system.security.vo.JwtUserVO;
import com.weiho.scaffold.system.service.RoleService;
import com.weiho.scaffold.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Weiho
 * @date 2022/7/29
 */
@Service("userDetailsService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new BadRequestException("账号不存在！");
        } else {
            if (!user.isEnabled()) {
                throw new BadRequestException("账号未激活！");
            }
            return createJwtUserVO(user);
        }
    }

    /**
     * 创建JwtUserVO对象
     *
     * @param user 用户初始对象
     * @return UserDetails
     */
    private UserDetails createJwtUserVO(User user) {
        JwtUserVO jwtUserVO = userService.getBaseJwtUserVO(user.getUsername());
        jwtUserVO.setCreateTime(DateUtils.getNowTimestamp());
        jwtUserVO.setAuthorities(roleService.mapToGrantedAuthorities(user));
        return jwtUserVO;
    }
}
