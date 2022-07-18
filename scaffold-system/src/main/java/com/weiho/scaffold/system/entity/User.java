package com.weiho.scaffold.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.weiho.scaffold.mp.entity.CommonEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("user")
@ApiModel(description = "User")
@EqualsAndHashCode(callSuper = true)
public class User extends CommonEntity {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)//设置为数据库自增
    private Integer id;

    @ApiModelProperty(value = "用户名")
    @TableField(value = "username")
    private String username;

    @ApiModelProperty(value = "密码")
    @TableField(value = "password")
    private String password;

    @ApiModelProperty(value = "是否启用")
    @TableField(value = "enabled")
    private boolean enabled;

    @ApiModelProperty(value = "是否锁定")
    @TableField(value = "locked")
    private boolean locked;
}
