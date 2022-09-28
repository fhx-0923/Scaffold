package com.weiho.scaffold.system.entity.vo;

import com.weiho.scaffold.mp.entity.CommonEntity;
import com.weiho.scaffold.system.entity.Menu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;

/**
 * @author Weiho
 * @since 2022/9/28
 */
@Setter
@Getter
@ToString
@ApiModel("前端用户VO")
public class RoleVO extends CommonEntity implements Serializable {
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("角色名称")
    private String name;

    @ApiModelProperty("角色中文名称")
    private String nameZhCn;

    @ApiModelProperty("角色中国香港名称")
    private String nameZhHk;

    @ApiModelProperty("角色中国台湾名称")
    private String nameZhTw;

    @ApiModelProperty("角色英文名称")
    private String nameEnUs;

    @ApiModelProperty("角色级别")
    private Integer level;

    @ApiModelProperty("功能权限")
    private String permission;

    @ApiModelProperty("能访问的菜单集合")
    private Set<Menu> menus;
}
