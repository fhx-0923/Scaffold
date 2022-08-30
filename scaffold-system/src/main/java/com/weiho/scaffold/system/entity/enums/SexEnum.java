package com.weiho.scaffold.system.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 使用了MapStruct进行对象转换的时候，Enum枚举默认将属性名带去转化，而不是key或者value
 * 在书写DTO的时候最好是使用枚举类型而不是String类型
 */
@Getter
public enum SexEnum {
    WOMAN(0, "女"),
    MAN(1, "男");

    @EnumValue
    private final Integer key;

    @JsonValue
    private final String display;

    SexEnum(Integer key, String display) {
        this.key = key;
        this.display = display;
    }
}
