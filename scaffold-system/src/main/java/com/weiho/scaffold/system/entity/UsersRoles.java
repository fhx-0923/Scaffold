package com.weiho.scaffold.system.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * <p>
 * 用户角色中间表
 * </p>
 *
 * @author Weiho
 * @since 2022-09-21
 */
@Alias("usersRoles")
@Getter
@Setter
@ToString
@ApiModel(value = "UsersRoles对象", description = "用户角色中间表")
public class UsersRoles implements Serializable {

    @ApiModelProperty("用户主键ID")
    private Long userId;

    @ApiModelProperty("角色主键ID")
    private Long roleId;
}
