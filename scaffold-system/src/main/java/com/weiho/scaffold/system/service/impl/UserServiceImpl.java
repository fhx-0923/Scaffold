package com.weiho.scaffold.system.service.impl;

import com.weiho.scaffold.mp.service.impl.CommonServiceImpl;
import com.weiho.scaffold.system.entity.User;
import com.weiho.scaffold.system.mapper.UserMapper;
import com.weiho.scaffold.system.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends CommonServiceImpl<UserMapper, User> implements UserService {
}
