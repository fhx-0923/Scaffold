package com.weiho.scaffold.system.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Weiho
 * @since 2022/9/22
 */
@Getter
@AllArgsConstructor
public enum AuditEnum {
    AUDIT_OK(1, "已审核"),
    AUDIT_NO(0, "未审核");

    @EnumValue
    private final Integer key;

    @JsonValue
    private final String display;
}
