package com.weiho.scaffold.system.entity.vo;

import com.weiho.scaffold.tools.mail.enums.EmailTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Weiho
 * @date 2022/9/6
 */
@Data
@Api(tags = "修改邮箱参数对象")
public class VerificationVO {
    @NotBlank(message = "新邮箱不能为空")
    @ApiModelProperty(value = "新邮箱", required = true)
    private String newEmail;

    @ApiModelProperty(value = "UUID", required = true)
    private String uuid;

    @NotBlank(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码", required = true)
    private String code;

    @ApiModelProperty(value = "邮箱后缀", required = true)
    private EmailTypeEnum suffix;

    @NotBlank(message = "当前密码不能为空")
    @ApiModelProperty(value = "当前账号密码", required = true)
    private String password;
}
