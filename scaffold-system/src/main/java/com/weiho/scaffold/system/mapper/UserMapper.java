package com.weiho.scaffold.system.mapper;

import com.weiho.scaffold.mp.mapper.CommonMapper;
import com.weiho.scaffold.system.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper extends CommonMapper<User> {
}
