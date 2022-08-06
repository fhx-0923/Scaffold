package com.weiho.scaffold.system.security.token.utils;

import com.alibaba.fastjson.JSON;
import com.weiho.scaffold.common.config.system.ScaffoldSystemProperties;
import com.weiho.scaffold.common.util.redis.RedisUtils;
import com.weiho.scaffold.common.util.string.StringUtils;
import com.weiho.scaffold.system.security.vo.JwtUserVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author Weiho
 * @date 2022/7/29
 */
@Slf4j
@Component
public class TokenUtils {
    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ScaffoldSystemProperties.JwtProperties jwtProperties;

    /**
     * 获得 Claims
     *
     * @param token Token
     * @return Claims
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtProperties.getSecret())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.warn("JWT -> 从Token中获取用户信息错误[{}]", e.getMessage());
            claims = null;
        }
        return claims;
    }

    /**
     * 根据token获取用户名
     *
     * @param token woken
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims != null ? claims.getSubject() : null;
    }

    /**
     * 获取过期时间
     *
     * @param token Token
     * @return Date
     */
    public Date getExpiredFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims != null ? claims.getExpiration() : null;
    }

    /**
     * 计算过期时间
     *
     * @return Date
     */
    private Date generateExpired() {
        return new Date(System.currentTimeMillis() + jwtProperties.getTokenValidityInSeconds() * 1000);
    }

    /**
     * 判断 Token 是否过期
     *
     * @param token Token
     * @return Boolean
     */
    private Boolean isTokenExpired(String token) {
        Date expirationDate = getExpiredFromToken(token);
        return expirationDate.before(new Date());
    }

    /**
     * 生成 Token
     *
     * @param userDetails 用户信息
     * @return String
     */
    public String generateToken(UserDetails userDetails) {
        //获取加密
        String secret = jwtProperties.getSecret();
        //根据用户名生成token
        String token = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setExpiration(generateExpired())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        //生成Redis的key (采用token作为key一部分防止相同用户多线程登录key唯一造成冲突)
        String key = jwtProperties.getTokenKey() + userDetails.getUsername() + ":" + token;
        //存入Redis
        redisUtils.set(key, token, jwtProperties.getTokenValidityInSeconds() / 1000);
        //将用户信息存入Redis
        putUserDetails(userDetails);
        return token;
    }

    /**
     * 验证Token是否合法
     *
     * @param token 传入token
     * @return Boolean
     */
    public Boolean validateToken(String token) {
        //从token中获取用户名
        final String username = getUsernameFromToken(token);
        //组成Redis中的key
        String key = jwtProperties.getTokenKey() + username + ":" + token;
        //从Redis中获取指定key的value
        Object data = redisUtils.get(key);
        //Redis中查询出来的Token
        String redisToken = data == null ? null : data.toString();
        return StringUtils.isNotEmpty(token)//保证不为空
                && !isTokenExpired(token)//token不过期
                && token.equals(redisToken);//两个token一致
    }

    /**
     * 移除 Token
     *
     * @param token Token
     */
    public void removeToken(String token) {
        final String username = getUsernameFromToken(token);
        String key = jwtProperties.getTokenKey() + username + ":" + token;
        redisUtils.del(key);
        delUserDetails(username);
    }

    /**
     * 获取用户信息
     *
     * @param token token
     * @return 用户信息
     */
    protected String getUserDetailsString(String token) {
        final String username = getUsernameFromToken(token);
        Object data = redisUtils.get(jwtProperties.getDetailKey() + username);
        return data.toString();
    }

    /**
     * 获得用户信息
     *
     * @param token Token
     * @return UserDetails
     */
    public UserDetails getUserDetails(String token) {
        String userDetailsString = getUserDetailsString(token);
        if (userDetailsString != null) {
            return JSON.parseObject(formatUserDetailsString(userDetailsString), JwtUserVO.class);
        }
        return null;
    }

    /**
     * 规范化json字符串，去除字符串中的permission数组，防止FastJson对中括号报异常
     *
     * @param userDetailsString 传入的Json字符串
     * @return 能正常反序列化的字符串
     */
    public String formatUserDetailsString(String userDetailsString) {
        return userDetailsString.replace(userDetailsString.substring(userDetailsString.indexOf("\"permission\""), userDetailsString.indexOf("]") + 2), "");
    }

    /**
     * 存储用户信息
     *
     * @param userDetails 用户信息
     */
    private void putUserDetails(UserDetails userDetails) {
        redisUtils.set(jwtProperties.getDetailKey() + userDetails.getUsername(), JSON.toJSON(userDetails), jwtProperties.getTokenValidityInSeconds() / 1000);
    }

    /**
     * 删除用户信息
     *
     * @param username 用户名
     */
    public void delUserDetails(String username) {
        redisUtils.del(jwtProperties.getDetailKey() + username);
    }

    /**
     * 从请求中获取Token
     *
     * @param request request对象
     * @return Token
     */
    public String getTokenFromRequest(HttpServletRequest request) {
        final String requestHeader = request.getHeader(jwtProperties.getHeader());
        if (requestHeader != null && requestHeader.startsWith(jwtProperties.getTokenStartWith())) {
            return requestHeader.substring(jwtProperties.getTokenStartWith().length());
        }
        return null;
    }
}
