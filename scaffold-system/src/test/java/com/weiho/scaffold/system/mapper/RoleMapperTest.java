package com.weiho.scaffold.system.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Weiho
 * @date 2022/8/4
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RoleMapperTest {

    @Autowired
    private RoleMapper roleMapper;

    @Test
    void findByUserId() {
        System.err.println(roleMapper.findByUserId(1L));
    }
}