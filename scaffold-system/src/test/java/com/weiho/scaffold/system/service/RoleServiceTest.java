package com.weiho.scaffold.system.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.weiho.scaffold.system.entity.User;
import com.weiho.scaffold.system.security.vo.JwtUserVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

/**
 * @author Weiho
 * @date 2022/8/4
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RoleServiceTest {

    @Autowired
    private RoleService roleService;

    @Test
    void mapToGrantedAuthorities() throws JsonProcessingException {
        User user = new User();
        user.setUsername("root");
        user.setId(4L);
        Collection<SimpleGrantedAuthority> list = roleService.mapToGrantedAuthorities(user);
        JwtUserVO jwtUserVO = new JwtUserVO();
        jwtUserVO.setAuthorities(list);
        System.err.println(jwtUserVO);
//        System.err.println(new ArrayList<>(list).stream().map(SimpleGrantedAuthority::getAuthority).collect(Collectors.toList()));
    }
}