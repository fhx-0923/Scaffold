package com.weiho.scaffold.system.mapper;

import com.weiho.scaffold.system.entity.User;
import com.weiho.scaffold.system.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserMapperTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Test
    void dd() {
        User user = new User();
        user.setId(4);
        userMapper.insert(user);

//        userService.removeById(user);
    }
}