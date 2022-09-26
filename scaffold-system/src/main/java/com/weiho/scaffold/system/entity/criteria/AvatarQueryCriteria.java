package com.weiho.scaffold.system.entity.criteria;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Weiho
 * @since 2022/9/26
 */
@Data
@ToString
@ApiModel("头像查询实体")
public class AvatarQueryCriteria {
    @ApiModelProperty("用户名模糊查询")
    private String blurry;

    @ApiModelProperty("创建时间范围")
    private List<Timestamp> createTime;
}
