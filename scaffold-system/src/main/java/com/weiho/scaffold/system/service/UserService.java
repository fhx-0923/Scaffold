package com.weiho.scaffold.system.service;

import com.weiho.scaffold.mp.service.CommonService;
import com.weiho.scaffold.system.entity.User;
import com.weiho.scaffold.system.security.vo.JwtUserVO;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author Weiho
 * @since 2022-08-04
 */
public interface UserService extends CommonService<User> {
    /**
     * 根据用户名生成基础的JwtUserVO对象(权限为空)
     *
     * @param username 用户名
     * @return 权限为空的JwtUserVO对象
     */
    JwtUserVO getBaseJwtUserVO(String username);

    /**
     * 根据用户名查找用户信息
     *
     * @param username 用户名
     * @return 返回的用户信息对象
     */
    User findByUsername(String username);
}
