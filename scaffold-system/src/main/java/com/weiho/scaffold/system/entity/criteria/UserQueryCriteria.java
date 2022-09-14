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
 * @date 2022/9/14
 */
@Data
@ApiModel("用户查询实体")
public class UserQueryCriteria {
    @Query
    @ApiModelProperty("主键")
    private Long id;

    @Query(blurry = "email,username")
    @ApiModelProperty("模糊查询字段")
    private String blurry;

    @Query
    @ApiModelProperty("账号是否启用")
    private Boolean enabled;

    @Query(type = QueryTypeEnum.BETWEEN)
    @ApiModelProperty("注册时间的范围")
    private List<Timestamp> createTime;
}
