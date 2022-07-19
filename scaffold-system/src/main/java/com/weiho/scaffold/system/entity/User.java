package com.weiho.scaffold.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.weiho.scaffold.mp.entity.CommonEntity;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author Weiho
 * @since 2022-07-19
 */
@Getter
@Setter
@ToString
@TableName("user")
@ApiModel(value = "User对象", description = "系统用户表")
public class User extends CommonEntity {

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("头像ID")
    @TableField("avatar_id")
    private Long avatarId;

    @ApiModelProperty("用户名")
    @TableField("username")
    private String username;

    @ApiModelProperty("密码")
    @TableField("password")
    private String password;

    @ApiModelProperty("性别 0-女 1-男")
    @TableField("sex")
    private Integer sex;

    @ApiModelProperty("邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty("手机号码")
    @TableField("phone")
    private String phone;

    @ApiModelProperty("状态：1启用 0禁用")
    @TableField("enabled")
    private Long enabled;

    @ApiModelProperty("最后修改密码时间")
    @TableField("last_pass_reset_time")
    private Date lastPassResetTime;

}
