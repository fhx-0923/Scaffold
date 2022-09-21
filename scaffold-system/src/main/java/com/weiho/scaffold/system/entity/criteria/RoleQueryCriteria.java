package com.weiho.scaffold.system.entity.criteria;

import com.weiho.scaffold.mp.annotation.Query;
import com.weiho.scaffold.mp.enums.QueryTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Weiho
 * @since 2022/9/14
 */
@Data
@ApiModel("角色查询实体")
public class RoleQueryCriteria {
    @Query(blurry = "name,permission")
    @ApiModelProperty("模糊查询字段")
    private String blurry;

    @Query(type = QueryTypeEnum.BETWEEN)
    @ApiModelProperty("创建时间范围")
    private List<Timestamp> createTime;
}
