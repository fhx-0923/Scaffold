package com.weiho.scaffold.system.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.weiho.scaffold.common.util.message.I18nMessagesUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 使用了MapStruct进行对象转换的时候，Enum枚举默认将属性名带去转化，而不是key或者value
 * 在书写DTO的时候最好是使用枚举类型而不是String类型
 */
@Getter
@AllArgsConstructor
public enum SexEnum {
    WOMAN(0, I18nMessagesUtils.get("sexEnum.female")),
    MAN(1, I18nMessagesUtils.get("sexEnum.male"));

    @EnumValue
    private final Integer key;

    @JsonValue
    private final String display;
}
