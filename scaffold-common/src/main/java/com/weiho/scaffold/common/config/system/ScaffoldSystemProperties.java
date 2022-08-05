package com.weiho.scaffold.common.config.system;

import com.weiho.scaffold.common.exception.CaptchaException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 系统配置类
 *
 * @author Weiho
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "scaffold")
public class ScaffoldSystemProperties {
    private String version;
    @Autowired
    private SwaggerProperties swaggerProperties;
    @Autowired
    private RateLimiterProperties rateLimiterProperties;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private CodeProperties codeProperties;
    @Autowired
    private MonitorProperties monitorProperties;
    @Autowired
    private TaskThreadPoolProperties threadPoolProperties;
    @Autowired
    private RSAProperties rsaProperties;

    @Getter
    @Setter
    @Configuration
    @ConfigurationProperties(prefix = "scaffold.swagger")
    public static class SwaggerProperties {
        //是否开启Swagger接口文档
        private Boolean enabled;
        //Swagger标题
        private String title;
        //描述
        private String description;
        //版本
        private String version;
    }

    @Getter
    @Setter
    @Configuration
    @ConfigurationProperties(prefix = "scaffold.limit")
    public static class RateLimiterProperties {
        //是否开启限流
        private boolean enabled = true;
    }

    @Getter
    @Setter
    @Configuration
    @ConfigurationProperties(prefix = "scaffold.jwt")
    public static class JwtProperties {
        //token的头部的属性key
        private String header;
        //令牌token的前缀
        private String tokenStartWith;
        //token加密的编码
        private String secret;
        //token过期的时间
        private Long tokenValidityInSeconds;
        //token缓存中的Key前缀
        private String onlineKey;
        //是否允许用户单次登录
        private Boolean singleLogin;

        public String getTokenStartWith() {
            return tokenStartWith + " ";
        }
    }

    @Getter
    @Setter
    @Configuration
    @ConfigurationProperties(prefix = "scaffold.code")
    public static class CodeProperties {
        //验证码缓存前缀
        private String codeKey;
        //验证码有效时间
        private Long expiration;
        //验证码长度
        private int height = 40;
        //验证码宽度
        private int width = 140;
        //验证码位数
        private int len = 2;
        //验证码类型
        private Class<?> type;

        public CodeProperties() {
            try {
                this.type = Class.forName("com.wf.captcha.ArithmeticCaptcha");
            } catch (ClassNotFoundException e) {
                throw new CaptchaException("验证码生成异常,请联系管理员");
            }
        }
    }

    @Getter
    @Setter
    @Configuration
    @ConfigurationProperties(prefix = "scaffold.monitor")
    public static class MonitorProperties {
        //是否开启系统参数监测
        private boolean enabled = false;
    }

    @Getter
    @Setter
    @Configuration
    @ConfigurationProperties(prefix = "scaffold.thread-pool")
    public static class TaskThreadPoolProperties {
        //核心线程数
        private int corePoolSize;
        //最大线程数
        private int maxPoolSize;
        //队列大小
        private int queueCapacity;
        //活跃时间
        private int keepAliveSeconds;
        //线程名称前缀
        private String namePrefix;
    }

    @Getter
    @Setter
    @Configuration
    @ConfigurationProperties(prefix = "scaffold.rsa")
    public static class RSAProperties {
        //RSA私钥
        private String privateKey;
    }
}
