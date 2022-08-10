package com.weiho.scaffold.system.security.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.asymmetric.RSA;
import com.weiho.scaffold.common.config.system.ScaffoldSystemProperties;
import com.weiho.scaffold.common.exception.BadRequestException;
import com.weiho.scaffold.common.exception.CaptchaException;
import com.weiho.scaffold.common.util.redis.RedisUtils;
import com.weiho.scaffold.common.util.string.StringUtils;
import com.weiho.scaffold.system.security.service.LoginService;
import com.weiho.scaffold.system.security.token.utils.TokenUtils;
import com.weiho.scaffold.system.security.vo.AuthUserVO;
import com.weiho.scaffold.system.security.vo.JwtUserVO;
import com.wf.captcha.base.Captcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private ScaffoldSystemProperties properties;

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private OnlineUserServiceImpl onlineUserService;

    @Override
    public Map<String, Object> getVerifyCodeInfo() {
        Captcha captcha;
        //验证码类(3位运算)
        try {
            captcha = (Captcha) Class.forName(properties.getCodeProperties().getType().getName()).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new CaptchaException("验证码生成异常,请联系管理员");
        }
        //验证码设置高度
        captcha.setHeight(properties.getCodeProperties().getHeight());
        //验证码设置宽度
        captcha.setWidth(properties.getCodeProperties().getWidth());
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
        String uuid = properties.getCodeProperties().getCodeKey() + IdUtil.simpleUUID();
        //保存到Redis中
        redisUtils.set(uuid, result, properties.getCodeProperties().getExpiration(), TimeUnit.MINUTES);
        return new HashMap<String, Object>(3) {{
            put("type", "VerifyCode");
            put("uuid", uuid);
            put("code", captcha.toBase64());
        }};
    }

    @Override
    public Map<String, Object> login(AuthUserVO authUserVO, HttpServletRequest request) {
        //使用RSA私钥解密
        RSA rsa = new RSA(properties.getRsaProperties().getPrivateKey(), null);
        //对密码进行解密
//        String password = new String(rsa.decrypt(authUserVO.getPassword(), KeyType.PrivateKey));
        //测试用
        String password = authUserVO.getPassword();
        //查询验证码
        String code = (String) redisUtils.get(authUserVO.getUuid());
        //清除验证码
        redisUtils.del(authUserVO.getUuid());
        if (StringUtils.isBlank(code)) {
            throw new BadRequestException("验证码不存在或已过期！");
        }
        if (StringUtils.isBlank(authUserVO.getCode()) || !authUserVO.getCode().equalsIgnoreCase(code)) {
            throw new BadRequestException("验证码错误！");
        }
        //手动授权
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authUserVO.getUsername(), password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        //放入Security安全上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //获取用户信息生成token令牌
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = tokenUtils.generateToken(userDetails);
        final JwtUserVO jwtUserVO = (JwtUserVO) authentication.getPrincipal();
        //保存用户在线信息
        onlineUserService.save(jwtUserVO, token, request);
        //若只允许单次登录则踢掉之前已经上线的token
        if (properties.getJwtProperties().getSingleLogin()) {
            onlineUserService.checkLoginOnUser(authUserVO.getUsername(), token);
        }
        return new HashMap<String, Object>() {{
            put("token", properties.getJwtProperties().getTokenStartWith() + token);
            put("userInfo", jwtUserVO);
        }};
    }
}
