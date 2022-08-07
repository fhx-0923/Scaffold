package com.weiho.scaffold.logging.enums;

import lombok.Getter;

/**
 * @author Weiho
 * @date 2022/8/7
 */
@Getter
public enum LogTypeEnum {
    INFO("INFO"),
    ERROR("ERROR");

    private final String msg;

    LogTypeEnum(String msg) {
        this.msg = msg;
    }
}
