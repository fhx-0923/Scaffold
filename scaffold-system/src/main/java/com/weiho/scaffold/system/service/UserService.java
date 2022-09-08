package com.weiho.scaffold.system.service;

import com.weiho.scaffold.mp.service.CommonService;
import com.weiho.scaffold.system.entity.User;
import com.weiho.scaffold.system.security.vo.JwtUserVO;
import org.springframework.web.multipart.MultipartFile;

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
     * 根据用户实体生成基础的JwtUserVO对象(权限为空)
     *
     * @param user 用户实体
     * @return 权限为空的JwtUserVO对象
     */
    JwtUserVO getBaseJwtUserVO(User user);

    /**
     * 根据用户名查找用户信息
     *
     * @param username 用户名
     * @return 返回的用户信息对象
     */
    User findByUsername(String username);

    /**
     * 用户修改密码
     *
     * @param username        用户名
     * @param encryptPassword 加密后的新密码
     */
    void updatePass(String username, String encryptPassword);

    /**
     * 用户修改邮箱
     *
     * @param username 用户名
     * @param newEmail 新邮箱
     */
    void updateEmail(String username, String newEmail);

    /**
     * 用户修改头像
     *
     * @param multipartFile 头像
     */
    void updateAvatar(MultipartFile multipartFile);

    /**
     * 更新缓存
     *
     * @param newUser 新的用户信息
     * @return /
     */
    User updateCache(User newUser);
}
