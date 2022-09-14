package com.weiho.scaffold.system.service.impl;

import cn.hutool.core.io.FileUtil;
import com.github.pagehelper.PageInfo;
import com.weiho.scaffold.common.config.system.ScaffoldSystemProperties;
import com.weiho.scaffold.common.util.date.DateUtils;
import com.weiho.scaffold.common.util.date.FormatEnum;
import com.weiho.scaffold.common.util.file.FileUtils;
import com.weiho.scaffold.common.util.page.PageUtils;
import com.weiho.scaffold.common.util.security.SecurityUtils;
import com.weiho.scaffold.common.util.string.StringUtils;
import com.weiho.scaffold.mp.core.QueryHelper;
import com.weiho.scaffold.mp.service.impl.CommonServiceImpl;
import com.weiho.scaffold.system.entity.Avatar;
import com.weiho.scaffold.system.entity.User;
import com.weiho.scaffold.system.entity.convert.JwtUserVOConvert;
import com.weiho.scaffold.system.entity.convert.UserVOConvert;
import com.weiho.scaffold.system.entity.criteria.UserQueryCriteria;
import com.weiho.scaffold.system.entity.vo.UserVO;
import com.weiho.scaffold.system.mapper.AvatarMapper;
import com.weiho.scaffold.system.mapper.RoleMapper;
import com.weiho.scaffold.system.mapper.UserMapper;
import com.weiho.scaffold.system.security.vo.JwtUserVO;
import com.weiho.scaffold.system.service.AvatarService;
import com.weiho.scaffold.system.service.RoleService;
import com.weiho.scaffold.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl extends CommonServiceImpl<UserMapper, User> implements UserService {
    private final AvatarService avatarService;
    private final AvatarMapper avatarMapper;
    private final RoleMapper roleMapper;
    private final RoleService roleService;
    private final JwtUserVOConvert jwtUserVOConvert;
    private final ScaffoldSystemProperties properties;
    private final UserVOConvert userVOConvert;

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateEmail(String username, String newEmail) {
        this.getBaseMapper().updateEmail(username, newEmail);
    }

    @Override
    public void updateAvatar(MultipartFile multipartFile) {
        User user = findByUsername(SecurityUtils.getUsername());
        Avatar avatar = avatarService.selectByAvatarId(user.getAvatarId(), user.getUsername());
        String oldFileName = avatar.getRealName();
        String oldFileNamePath = properties.getFileProperties().getLocalAddressPrefix() + oldFileName;
        File file = FileUtils.upload(multipartFile, properties.getFileProperties().getLocalAddressPrefix());
        avatar.setRealName(file.getName());
        avatar.setPath(properties.getFileProperties().getServerAddressPrefix() + file.getName());
        avatar.setSize(FileUtils.getSize(multipartFile.getSize()));
        avatarService.updateAvatar(avatar, user.getUsername());
        if (StringUtils.isNotBlank(oldFileNamePath)) {
            FileUtil.del(oldFileNamePath);
        }
    }

    @Override
    @CachePut(value = "Scaffold:Commons:User", key = "'loadUserByUsername:' + #p0.getUsername()", unless = "#result == null || #result.enabled == false")
    public User updateCache(User newUser) {
        return newUser;
    }

    @Override
    public Map<String, Object> getUserList(UserQueryCriteria criteria, Pageable pageable) {
        startPage(pageable);
        PageInfo<UserVO> pageInfo = new PageInfo<>(this.getAll(criteria));
        return PageUtils.toPageContainer(pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    public List<UserVO> getAll(UserQueryCriteria criteria) {
        List<User> users = this.getBaseMapper().selectList(CastUtils.cast(QueryHelper.getQueryWrapper(User.class, criteria)));
        List<UserVO> userVOS = new ArrayList<>();
        for (User user : users) {
            UserVO userVO = userVOConvert.toPojo(user);
            userVO.setAvatar(avatarMapper.selectById(user.getAvatarId()));
            userVO.setRoles(roleMapper.findSetByUserId(user.getId()));
            userVOS.add(userVO);
        }
        return userVOS;
    }
}
