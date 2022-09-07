package com.weiho.scaffold.tools.mail.service;

import com.weiho.scaffold.tools.mail.entity.vo.VerificationCodeVO;

import java.util.Map;

/**
 * @author Weiho
 * @date 2022/9/6
 */
public interface VerificationCodeService {
    /**
     * 构造邮箱的内容
     *
     * @param codeVO 传入的邮箱和类型
     * @return 邮箱的内容和验证码UUID
     */
    Map<String, Object> generatorEmailInfo(VerificationCodeVO codeVO);
}
