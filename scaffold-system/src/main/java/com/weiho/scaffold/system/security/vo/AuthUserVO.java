package com.weiho.scaffold.system.security.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 前端登录对象
 *
 * @author Weiho
 * @date 2022/7/29
 */
@Getter
@Setter
@ApiModel("前端登录VO对象")
public class AuthUserVO {
    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty("用户名")
    private String username;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty("密码")
    private String password;

    @NotBlank(message = "验证码不能为空")
    @ApiModelProperty("验证码")
    private String code;

    @ApiModelProperty("UUID")
    private String uuid;

    @Override
    public String toString() {
        return "AuthUserVO{username=" + username + ", password= ******}";
    }
}
