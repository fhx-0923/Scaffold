package com.weiho.scaffold.system.security.service;

import java.util.Map;

public interface LoginService {
    /**
     * 获取验证码的Base64编码和uuid
     *
     * @return 验证码Base64编码和uuid, Map的key分别为 "code_src" 和 "uuid"
     */
    Map<String, Object> getVerifyCodeInfo() throws ClassNotFoundException, InstantiationException, IllegalAccessException;
}
