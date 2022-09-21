package com.weiho.scaffold.common.sensitive.enums;

import com.weiho.scaffold.common.util.string.StringUtils;
import com.weiho.scaffold.common.util.verify.VerifyUtils;

import java.util.function.Function;

/**
 * 脱敏策略
 *
 * @author Weiho
 * @since 2022/8/24
 */
public enum SensitiveStrategy {
    /**
     * 用户名
     */
    USERNAME(SensitiveStrategy::desensitizeForUsername),

    /**
     * 身份证号
     */
    ID_CARD(SensitiveStrategy::desensitizeForIDCard),

    /**
     * 电话号码
     */
    PHONE(SensitiveStrategy::desensitizeForPhone),

    /**
     * 邮箱
     */
    EMAIL(SensitiveStrategy::desensitizeForEmail);

    private final Function<String, String> desensitize;

    SensitiveStrategy(Function<String, String> desensitize) {
        this.desensitize = desensitize;
    }

    public Function<String, String> desensitize() {
        return desensitize;
    }

    /**
     * 为用户名脱敏(只将姓名的第二个字脱敏)
     *
     * @param username 原始字段值
     * @return 脱敏后
     */
    public static String desensitizeForUsername(String username) {
        if (username.length() == 0 || username.length() == 1) {
            return "This doesn't look like a domestic name.";
        } else if (username.length() < 4) {
            return username.replace(username.charAt(1), '*');
        } else {
            return username.replace(username.charAt(2), '*');
        }
    }

    /**
     * 为身份证脱敏(将身份证号码11 - 16位脱敏)
     *
     * @param idCard 身份证号
     * @return 脱敏后
     */
    public static String desensitizeForIDCard(String idCard) {
        if (!VerifyUtils.isIdCard(idCard)) {
            return "This doesn't look like ID number.";
        } else {
            return idCard.replace(idCard.substring(10, 16), "******");
        }
    }

    /**
     * 为电话号码脱敏(将电话号码4 - 7位脱敏)
     *
     * @param phone 电话号码
     * @return 脱敏后
     */
    public static String desensitizeForPhone(String phone) {
        if (!VerifyUtils.isMobileExact(phone)) {
            return "This doesn't look like a domestic phone number.";
        } else {
            return phone.replace(phone.substring(3, 7), "****");
        }
    }

    /**
     * 为邮箱脱敏(邮箱前缀仅显示第一个字母，前缀其他隐藏，用星号代替，@及后面的地址显示<例子:g**@163.com>)
     *
     * @param email 邮箱
     * @return 脱敏后
     */
    public static String desensitizeForEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return "";
        }
        int index = StringUtils.indexOf(email, "@");
        if (index <= 1) {
            return email;
        }
        return StringUtils.rightPad(StringUtils.left(email, 1), index, "*").concat(StringUtils.mid(email, index, StringUtils.length(email)));
    }
}
