package com.weiho.scaffold.common.util.date;

/**
 * 时间格式化枚举类
 *
 * @author Weiho
 */
public enum FormatEnum {
    /**
     * 年份 [yyyy] 格式
     */
    YYYY("yyyy"),

    /**
     * 年份-月份 [yyyy-MM] 格式
     */
    YYYY_MM("yyyy-MM"),

    /**
     * 年份-月份-日 [yyyy-MM-dd] 格式
     */
    YYYY_MM_DD("yyyy-MM-dd"),

    /**
     * 年份月份日 [yyyyMMdd] 格式
     */
    YYYYMMDD("yyyyMMdd"),

    /**
     * 年份-月份-日 时:分:秒 [yyyy-MM-dd HH:mm:ss] 格式
     */
    YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss");

    private final String format;

    FormatEnum(String format) {
        this.format = format;
    }

    /**
     * @return String
     */
    public String getFormat() {
        return format;
    }
}
