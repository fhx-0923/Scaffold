package com.weiho.scaffold.mp.annotation;

import com.weiho.scaffold.mp.enums.QueryTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 基于注解的查询条件构造
 *
 * @author yshopmall - <a href="https://gitee.com/guchengwuyue/yshopmall">参考链接</a>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Query {
    /**
     * 数据库的字段名
     */
    String propName() default "";

    /**
     * 查询类型（默认等值查询）
     */
    QueryTypeEnum type() default QueryTypeEnum.EQUAL;


    /**
     * 多字段模糊搜索，仅支持String类型字段，多个用逗号隔开, 如@Query(blurry = "email,username")
     */
    String blurry() default "";
}
