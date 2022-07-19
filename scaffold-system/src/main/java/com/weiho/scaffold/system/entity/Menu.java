package com.weiho.scaffold.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.weiho.scaffold.mp.entity.CommonEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 系统菜单表
 * </p>
 *
 * @author Weiho
 * @since 2022-07-19
 */
@Getter
@Setter
@ToString
@TableName("menu")
@ApiModel(value = "Menu对象", description = "系统菜单表")
public class Menu extends CommonEntity {

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("组件")
    @TableField("component")
    private String component;

    @ApiModelProperty("菜单名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("图标")
    @TableField("icon_cls")
    private String iconCls;

    @ApiModelProperty("后端使用的url")
    @TableField("url")
    private String url;

    @ApiModelProperty("前端使用的path")
    @TableField("path")
    private String path;

    @ApiModelProperty("是否保持激活")
    @TableField("keep_alive")
    private Long keepAlive;

    @ApiModelProperty("是否要求权限")
    @TableField("require_auth")
    private Long requireAuth;

    @ApiModelProperty("上级菜单ID")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty("是否启用")
    @TableField("enabled")
    private Long enabled;

}
