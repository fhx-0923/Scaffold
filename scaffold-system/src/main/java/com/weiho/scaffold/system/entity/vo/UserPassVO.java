package com.weiho.scaffold.system.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Weiho
 * @date 2022/9/5
 */
@Data
@ApiModel("用户修改密码实体")
public class UserPassVO {
    @NotBlank
    @ApiModelProperty(value = "旧密码", required = true)
    private String oldPassword;

    @NotBlank
    @ApiModelProperty(value = "新密码", required = true)
    private String newPassword;
}
