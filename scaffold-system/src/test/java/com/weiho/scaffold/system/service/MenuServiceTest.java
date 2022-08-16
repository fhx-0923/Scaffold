package com.weiho.scaffold.system.service;

import com.alibaba.fastjson.JSON;
import com.weiho.scaffold.common.config.system.ScaffoldSystemProperties;
import com.weiho.scaffold.common.util.redis.RedisUtils;
import com.weiho.scaffold.system.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Weiho
 * @date 2022/8/9
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MenuServiceTest {

    @Autowired
    private MenuService menuService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ScaffoldSystemProperties properties;

    @Test
    void findListByRoles() {
        Role role = new Role();
        role.setId(1L);
        List<Role> longs = new ArrayList<Role>() {{
            add(role);
        }};
        System.err.println(JSON.toJSONString(menuService.buildMenuList(menuService.buildTree(menuService.findListByRoles(longs, "root")))));
    }
}