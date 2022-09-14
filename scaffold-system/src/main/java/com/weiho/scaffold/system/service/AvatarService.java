package com.weiho.scaffold.system.service;

import com.weiho.scaffold.mp.service.CommonService;
import com.weiho.scaffold.system.entity.Avatar;

/**
 * <p>
 * 用户头像表 服务类
 * </p>
 *
 * @author Weiho
 * @since 2022-07-29
 */
public interface AvatarService extends CommonService<Avatar> {
    /**
     * 根据头像ID查询头像信息
     *
     * @param avatarId 头像ID
     * @param username 当前登录的用户名
     * @return 头像实体
     */
    Avatar selectByAvatarId(Long avatarId, String username);

    /**
     * 修改头像
     *
     * @param avatar 头像
     */
    @SuppressWarnings("all")
    Avatar updateAvatar(Avatar avatar, String username);
}
