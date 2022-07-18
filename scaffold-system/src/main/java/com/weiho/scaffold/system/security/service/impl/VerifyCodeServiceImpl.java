package com.weiho.scaffold.system.security.service.impl;

import cn.hutool.core.util.IdUtil;
import com.weiho.scaffold.common.config.system.ScaffoldSystemProperties;
import com.weiho.scaffold.common.util.redis.RedisUtils;
import com.weiho.scaffold.system.security.service.VerifyCodeService;
import com.wf.captcha.ArithmeticCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class VerifyCodeServiceImpl implements VerifyCodeService {
    @Autowired
    private ScaffoldSystemProperties.CodeProperties codeProperties;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public Map<String, Object> getVerifyCodeInfo(int width, int height) {
        //验证码类
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(width, height);
        //几位数运算
        captcha.setLen(2);
        //获取运算结果
        String result;
        try {
            result = new Double(Double.parseDouble(captcha.text())).intValue() + "";
        } catch (Exception e) {
            result = captcha.text();
        }
        //产生UUID，作为每个用户的唯一标识
        String uuid = codeProperties.getCodeKey() + IdUtil.simpleUUID();
        //保存到Redis中
        redisUtils.set(uuid, result, codeProperties.getExpiration(), TimeUnit.MINUTES);
        return new HashMap<String, Object>(2) {{
            put("uuid", uuid);
            put("code_src", captcha.toBase64());
        }};
    }
}
