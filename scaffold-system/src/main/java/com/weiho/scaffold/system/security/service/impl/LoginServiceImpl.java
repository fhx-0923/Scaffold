package com.weiho.scaffold.system.security.service.impl;

import cn.hutool.core.util.IdUtil;
import com.weiho.scaffold.common.config.system.ScaffoldSystemProperties;
import com.weiho.scaffold.common.exception.CaptchaException;
import com.weiho.scaffold.common.util.redis.RedisUtils;
import com.weiho.scaffold.system.security.service.LoginService;
import com.wf.captcha.base.Captcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private ScaffoldSystemProperties.CodeProperties codeProperties;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public Map<String, Object> getVerifyCodeInfo() {
        Captcha captcha;
        //验证码类(3位运算)
        try {
            captcha = (Captcha) Class.forName(codeProperties.getType().getName()).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new CaptchaException("验证码生成异常,请联系管理员");
        }
        //验证码设置高度
        captcha.setHeight(codeProperties.getHeight());
        //验证码设置宽度
        captcha.setWidth(codeProperties.getWidth());
        //验证码设置3位
        captcha.setLen(3);
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
        return new HashMap<String, Object>(3) {{
            put("type", "verifyCode");
            put("uuid", uuid);
            put("code_src", captcha.toBase64());
        }};
    }
}
