package com.weiho.scaffold.system.security.service;

import java.util.Map;

public interface VerifyCodeService {
    /**
     * 获取验证码的Base64编码和uuid
     *
     * @param width  验证码宽度
     * @param height 验证码高度
     * @return 验证码Base64编码和uuid, Map的key分别为 "code_src" 和 "uuid"
     */
    Map<String, Object> getVerifyCodeInfo(int width, int height);
}
