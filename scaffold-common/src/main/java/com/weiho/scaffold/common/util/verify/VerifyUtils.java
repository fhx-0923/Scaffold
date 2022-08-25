package com.weiho.scaffold.common.util.verify;

import com.weiho.scaffold.common.util.string.StringUtils;
import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

/**
 * 通过正则表达式判断字段是否正确
 * (手机号,固定电话,身份证号码,邮箱,url,车牌号,日期,ip地址,mac,人名)
 *
 * @author Weiho
 * @date 2022/8/24
 */
@UtilityClass
public class VerifyUtils {
    /**
     * 正则:手机号(简单),1开头 + 10位数字即可
     */
    private static final String REGEX_MOBILE_SIMPLE = "^1\\d{10}$";
    private static final Pattern PATTERN_REGEX_MOBILE_SIMPLE = Pattern.compile(REGEX_MOBILE_SIMPLE);

    /**
     * 正则:手机号(精确),已知3位前缀 + 8位数字
     * <p>
     * 移动:134、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188、198 <br/>
     * 联通:130、131、132、145、155、156、166、175、176、185、186 <br/>
     * 电信:133、149、153、173、177、180、181、189、199 <br/>
     * 虚拟运营商:
     * 电信：1700、1701、1702
     * 移动：1703、1705、1706
     * 联通：1704、1707、1708、1709、171
     * 卫星通讯:1349
     * <p/>
     */
    private static final String REGEX_MOBILE_EXACT = "^(13\\d|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18\\d|19[0-35-9])\\d{8}$";
    private static final Pattern PATTERN_REGEX_MOBILE_EXACT = Pattern.compile(REGEX_MOBILE_EXACT);

    /**
     * 正则:固话(带区号)7位或者8位
     */
    private static final String REGEX_TEL = "^(\\d{3,4}-)?\\d{6,8}$";
    private static final Pattern PATTERN_REGEX_TEL = Pattern.compile(REGEX_TEL);

    /**
     * 正则：身份证号码15位, 数字且关于生日的部分必须正确
     */
    private static final String REGEX_ID_CARD15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
    private static final Pattern PATTERN_REGEX_ID_CARD15 = Pattern.compile(REGEX_ID_CARD15);

    /**
     * 正则：身份证号码18位, 数字且关于生日的部分必须正确
     */
    private static final String REGEX_ID_CARD18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([\\dXx])$";
    private static final Pattern PATTERN_REGEX_ID_CARD18 = Pattern.compile(REGEX_ID_CARD18);

    /**
     * 正则：邮箱, 有效字符(不支持中文), 且中间必须有@,后半部分必须有.
     */
    private static final String REGEX_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    private static final Pattern PATTERN_REGEX_EMAIL = Pattern.compile(REGEX_EMAIL);

    /**
     * 正则：URL, 必须有"://",前面必须是英文,后面不能有空格
     */
    private static final String REGEX_URL = "[a-zA-z]+://[^\\s]*";
    private static final Pattern PATTERN_REGEX_URL = Pattern.compile(REGEX_URL);

    /**
     * 正则：yyyy-MM-dd格式的日期校验,已考虑平闰年
     */
    private static final String REGEX_DATE = "^(?:(?!0000)\\d{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:\\d{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$";
    private static final Pattern PATTERN_REGEX_DATE = Pattern.compile(REGEX_DATE);

    /**
     * 正则：IP地址
     */
    private static final String REGEX_IP = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";
    private static final Pattern PATTERN_REGEX_IP = Pattern.compile(REGEX_IP);

    /**
     * 正则：车牌号
     */
    private static final String REGEX_CAR = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{3,4}[A-Z0-9挂学警港澳]{1}$";
    private static final Pattern PATTERN_REGEX_CAR = Pattern.compile(REGEX_CAR);

    /**
     * 正则：人名
     */
    private static final String REGEX_NAME = "^([\\u4e00-\u9fa5]{1,20}|[a-zA-Z\\.\\s]{1,20})$";
    private static final Pattern PATTERN_REGEX_NAME = Pattern.compile(REGEX_NAME);

    /**
     * 正则：mac地址
     */
    private static final String REGEX_MAC = "([A-Fa-f\\d]{2}-){5}[A-Fa-f\\d]{2}";
    private static final Pattern PATTERN_REGEX_MAC = Pattern.compile(REGEX_MAC);

    /**
     * 验证手机号(简单)
     */
    public boolean isMobileSimple(String phone) {
        return isMatch(PATTERN_REGEX_MOBILE_SIMPLE, phone);
    }

    /**
     * 验证手机号（精确）
     */
    public static boolean isMobileExact(String phone) {
        return isMatch(PATTERN_REGEX_MOBILE_EXACT, phone);
    }

    /**
     * 验证固定电话号码
     */
    public static boolean isTel(String tel) {
        return isMatch(PATTERN_REGEX_TEL, tel);
    }

    /**
     * 验证15或18位身份证号码
     */
    public static boolean isIdCard(String idCard) {
        return isMatch(PATTERN_REGEX_ID_CARD18, idCard);
    }

    /**
     * 验证邮箱
     */
    public static boolean isEmail(String email) {
        return isMatch(PATTERN_REGEX_EMAIL, email);
    }

    /**
     * 验证URL
     */
    public static boolean isUrl(String url) {
        return isMatch(PATTERN_REGEX_URL, url);
    }

    /**
     * 验证yyyy-MM-dd格式的日期校验,已考虑平闰年
     */
    public static boolean isDate(String date) {
        return isMatch(PATTERN_REGEX_DATE, date);
    }

    /**
     * 验证IP地址
     */
    public static boolean isIp(String ip) {
        return isMatch(PATTERN_REGEX_IP, ip);
    }

    /**
     * 验证车牌号
     */
    public static boolean isCar(String carNum) {
        return isMatch(PATTERN_REGEX_CAR, carNum);
    }

    /**
     * 验证人名
     */
    public static boolean isName(String name) {
        return isMatch(PATTERN_REGEX_NAME, name);
    }

    /**
     * 验证mac
     */
    public static boolean isMac(String mac) {
        return isMatch(PATTERN_REGEX_MAC, mac);
    }

    /**
     * 正则判断
     *
     * @param pattern 正则表达式
     * @param str     输入字符串
     * @return boolean
     */
    public boolean isMatch(Pattern pattern, String str) {
        return StringUtils.isNoneEmpty(str) && pattern.matcher(str).matches();
    }

}
