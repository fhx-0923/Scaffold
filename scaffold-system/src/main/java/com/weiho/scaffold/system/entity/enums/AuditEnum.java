package com.weiho.scaffold.system.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.weiho.scaffold.common.util.message.I18nMessagesUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Weiho
 * @since 2022/9/22
 */
@Getter
@AllArgsConstructor
public enum AuditEnum {
    AUDIT_OK(1, I18nMessagesUtils.get("auditEnum.ok")),
    AUDIT_NO(0, I18nMessagesUtils.get("auditEnum.no"));

    @EnumValue
    private final Integer key;

    @JsonValue
    private final String display;
}
