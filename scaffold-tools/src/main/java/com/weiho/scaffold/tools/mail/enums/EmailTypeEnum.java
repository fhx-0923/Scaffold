package com.weiho.scaffold.tools.mail.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Weiho
 * @date 2022/9/7
 */
@Getter
@AllArgsConstructor
public enum EmailTypeEnum {
    NET_EASE_163(0, "@163.com"),
    NET_EASE_126(1, "@126.com"),
    NET_EASE_YEAH(2, "@yeah.net"),
    TENCENT_QQ(3, "@qq.com"),
    TENCENT_FOX_MAIL(4, "@foxmail.com"),
    ALICLOUD(5, "@aliyun.com"),
    SINA(6, "@sina.com"),
    SOHU(7, "@sohu.com"),
    MICROSOFT(8, "@outlook.com"),
    GOOGLE(9, "@gmail.com");

    @EnumValue
    private final Integer key;

    @JsonValue
    private final String emailSuffix;
}
