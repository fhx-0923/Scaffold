package com.weiho.scaffold.system.service.impl;

import com.github.pagehelper.PageInfo;
import com.weiho.scaffold.common.util.page.PageUtils;
import com.weiho.scaffold.mp.service.impl.CommonServiceImpl;
import com.weiho.scaffold.system.entity.Avatar;
import com.weiho.scaffold.system.entity.vo.AvatarVO;
import com.weiho.scaffold.system.mapper.AvatarMapper;
import com.weiho.scaffold.system.service.AvatarService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

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

    @Override
    @Cacheable(value = "Scaffold:Commons:Avatar", key = "'loadAvatarByUsername:' + #p1")
    public Avatar selectByAvatarId(Long avatarId, String username) {
        return this.getBaseMapper().selectById(avatarId);
    }

    @Override
    @CachePut(value = "Scaffold:Commons:Avatar", key = "'loadAvatarByUsername:' + #p1")
    public Avatar updateAvatar(Avatar avatar, String username) {
        this.getBaseMapper().updateById(avatar);
        return avatar;
    }

    @Override
    public Map<String, Object> selectAvatarList(String usernameLike, Pageable pageable) {
        startPage(pageable);
        PageInfo<AvatarVO> pageInfo = new PageInfo<>(this.getBaseMapper().selectAvatarList(usernameLike));
        return PageUtils.toPageContainer(pageInfo.getList(), pageInfo.getTotal());
    }
}
