package com.weiho.scaffold.common.util.result.enums;

import com.weiho.scaffold.common.util.result.code.StatusCode;
import lombok.Getter;

/**
 * 自定义状态码
 *
 * @author Weiho
 */
@Getter
public enum ResultCodeEnum implements StatusCode {
    SUCCESS(1200, "请求成功"),
    SYSTEM_FORBIDDEN(1403, "拒绝访问"),//限流
    BAD_REQUEST_ERROR(1400, "请求参数错误"),
    FAILED(1500, "服务器错误"),
    VALIDATE_ERROR(1406, "参数校验失败"),
    RESPONSE_PACK_ERROR(1502, "response返回包装失败");

    private final int code;
    private final String msg;

    ResultCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
