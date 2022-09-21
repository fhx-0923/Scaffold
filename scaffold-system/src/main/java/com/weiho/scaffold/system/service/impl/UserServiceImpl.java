package com.weiho.scaffold.system.service.impl;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageInfo;
import com.weiho.scaffold.common.config.system.ScaffoldSystemProperties;
import com.weiho.scaffold.common.exception.BadRequestException;
import com.weiho.scaffold.common.util.date.DateUtils;
import com.weiho.scaffold.common.util.date.FormatEnum;
import com.weiho.scaffold.common.util.file.FileUtils;
import com.weiho.scaffold.common.util.message.I18nMessagesUtils;
import com.weiho.scaffold.common.util.page.PageUtils;
import com.weiho.scaffold.common.util.security.SecurityUtils;
import com.weiho.scaffold.common.util.string.StringUtils;
import com.weiho.scaffold.common.util.validation.ValidationUtils;
import com.weiho.scaffold.mp.core.QueryHelper;
import com.weiho.scaffold.mp.service.impl.CommonServiceImpl;
import com.weiho.scaffold.redis.util.RedisUtils;
import com.weiho.scaffold.system.entity.Avatar;
import com.weiho.scaffold.system.entity.Role;
import com.weiho.scaffold.system.entity.User;
import com.weiho.scaffold.system.entity.UsersRoles;
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
import com.weiho.scaffold.system.service.UsersRolesService;
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
import java.util.Set;

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
    private final UsersRolesService usersRolesService;
    private final RedisUtils redisUtils;

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
    @Transactional(rollbackFor = Exception.class)
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(UserVO resource) {
        // 根据ID查找用户
        User user = this.getOne(new LambdaQueryWrapper<User>().eq(User::getId, resource.getId()));
        // 保存旧的用户名
        String oldUsername = user.getUsername();
        ValidationUtils.isNull(user.getId(), "User", "id", resource.getId());
        // 根据该实体中的用户名去查找
        User userUsername = this.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, resource.getUsername()));
        // 根据该实体中的邮箱去查找
        User userEmail = this.getOne(new LambdaQueryWrapper<User>().eq(User::getEmail, resource.getEmail()));

        // 验证用户名是否存在
        if (userUsername != null && !user.getId().equals(userUsername.getId())) {
            throw new BadRequestException(I18nMessagesUtils.get("username.exists"));
        }

        if (userEmail != null && !user.getEmail().equals(userEmail.getEmail())) {
            throw new BadRequestException(I18nMessagesUtils.get("email.exists"));
        }

        user.setUsername(resource.getUsername());
        user.setEmail(resource.getEmail());
        user.setSex(resource.getSex());
        user.setPhone(resource.getPhone());
        user.setEnabled(resource.isEnabled());
        user.setLastPassResetTime(resource.getLastPassResetTime());

        // 查看是否修改用户信息成功
        boolean result = this.saveOrUpdate(user);
        // 删除所有角色
        usersRolesService.deleteRolesByUserId(resource.getId());

        UsersRoles usersRoles = new UsersRoles();
        usersRoles.setUserId(resource.getId());
        Set<Role> set = resource.getRoles();
        for (Role role : set) {
            usersRoles.setRoleId(role.getId());
            if (result) {
                usersRolesService.save(usersRoles);
            }
        }

        // 若用户修改了角色，要手动删除一下缓存
        if (!resource.getRoles().equals(roleMapper.findSetByUserId(resource.getId()))) {
            String keyPermissions = "Scaffold:Commons:Permission::loadPermissionByUsername:" + oldUsername;
            String keyRoles = "Scaffold:Commons:Roles::loadRolesByUsername:" + oldUsername;
            // 手动删除缓存
            redisUtils.del(keyPermissions, keyRoles);
            // 手动调用一次更新缓存
            roleService.updateCacheForGrantedAuthorities(user.getId(), user.getUsername());
            roleService.updateCacheForRoleList(user);
        }
    }
}
