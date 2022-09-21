package com.weiho.scaffold.system.entity;

import com.weiho.scaffold.system.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Weiho
 * @since 2022/8/23
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EncryptTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void mybatisEncryptHandlerTest() {
        User user = userMapper.findByUsername("root");
        System.err.println(user.getEmail());
        System.err.println(user.getPhone());
    }
}