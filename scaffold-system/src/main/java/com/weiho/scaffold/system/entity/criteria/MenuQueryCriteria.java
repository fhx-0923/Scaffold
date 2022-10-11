package com.weiho.scaffold.system.entity.criteria;

import com.weiho.scaffold.mp.annotation.Query;
import com.weiho.scaffold.mp.enums.QueryTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Weiho
 * @since 2022/10/10
 */
@Data
@ToString
@ApiModel("菜单查询实体")
public class MenuQueryCriteria {
    @Query(blurry = "name,nameZhCn,nameZhHk,nameZhTw,nameEnUs,iconCls,permission")
    @ApiModelProperty("用户名模糊查询")
    private String blurry;

    @Query
    @ApiModelProperty("菜单是否启用")
    private Boolean enabled;

    @Query(type = QueryTypeEnum.BETWEEN)
    @ApiModelProperty("创建时间")
    private List<Timestamp> createTime;
}
