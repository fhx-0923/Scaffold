package com.weiho.scaffold.logging.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 日志操作状态(  0-正常  ,  1-失败)
 *
 * @author Weiho
 * @date 2022/8/6
 */
@Getter
public enum BusinessStatusEnum {
    /**
     * 成功
     */
    SUCCESS(0, "SUCCESS"),

    /**
     * 失败
     */
    FAIL(1, "FAIL");

    @EnumValue
    private final Integer key;

    @JsonValue
    private final String value;

    BusinessStatusEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }
}
