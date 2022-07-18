package com.weiho.scaffold.mp.enums;

import lombok.Getter;

/**
 * 分页类型枚举
 *
 * @author Weiho
 */
@Getter
public enum SortTypeEnum {
    ASC("asc"),
    DESC("desc");

    private final String sort;

    SortTypeEnum(String sort) {
        this.sort = sort;
    }
}
