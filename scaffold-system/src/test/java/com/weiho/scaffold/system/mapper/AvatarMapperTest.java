package com.weiho.scaffold.system.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Weiho
 * @date 2022/8/4
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AvatarMapperTest {

    @Autowired
    private AvatarMapper avatarMapper;

    @Test
    void selectByIdAvatar() {
        System.err.println(avatarMapper.selectById(1L));
    }
}