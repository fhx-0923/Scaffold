package com.weiho.scaffold.logging.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 日志业务类型枚举类
 *
 * @author Weiho
 * @date 2022/8/6
 */
@Getter
public enum BusinessTypeEnum {
    /**
     * 其他
     */
    OTHER(0, "OTHER"),

    /**
     * 插入
     */
    INSERT(1, "INSERT"),

    /**
     * 修改
     */
    UPDATE(2, "UPDATE"),

    /**
     * 删除
     */
    DELETE(3, "DELETE");

    @EnumValue
    private final Integer key;

    @JsonValue
    private final String value;

    BusinessTypeEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }
}
