package com.weiho.scaffold.common.handler.exception;

import com.weiho.scaffold.common.exception.SecurityException;
import com.weiho.scaffold.common.exception.*;
import com.weiho.scaffold.common.util.result.Result;
import com.weiho.scaffold.common.util.result.enums.ResultCodeEnum;
import com.weiho.scaffold.common.util.throwable.ThrowableUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 自定义全局异常处理
 *
 * @author Weiho
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理自定义异常 BadRequestException 类(错误请求)
     */
    @ExceptionHandler(value = BadRequestException.class)
    public Result badRequestException(BadRequestException e) {
        return Result.of(e.getCode(), e.getMsg(), e.getMessage());
    }

    /**
     * 处理服务器安全异常 SecurityException 类
     */
    @ExceptionHandler(value = SecurityException.class)
    public Result securityException(SecurityException e) {
        return Result.of(e.getCode(), e.getMsg(), e.getMessage());
    }

    /**
     * 处理Jackson序列化错误
     */
    @ExceptionHandler(value = ResponsePackException.class)
    public Result responsePackExceptionHandler(ResponsePackException e) {
        return Result.of(e.getCode(), e.getMsg(), e.getMessage());
    }

    /**
     * 处理限流异常 RateLimitException 类(限流警告)
     */
    @ExceptionHandler(value = RateLimitException.class)
    public Result rateLimitException(RateLimitException e) {
        return Result.of(e.getCode(), e.getMsg(), e.getMessage());
    }

    /**
     * 处理接口上@Validated注解验证的实体参数异常
     */
    @ExceptionHandler({BindException.class})
    public Result bindException(BindException e) {
        //获取堆栈上的信息
        log.error(ThrowableUtils.getStackTrace(e));
        ObjectError error = e.getBindingResult().getAllErrors().get(0);
        return Result.of(ResultCodeEnum.VALIDATE_ERROR, error.getDefaultMessage());
    }

    /**
     * 处理验证码生成异常 CaptchaException 类
     */
    @ExceptionHandler({CaptchaException.class})
    public Result captchaException(CaptchaException e) {
        return Result.of(e.getCode(), e.getMsg(), e.getMessage());
    }

    /**
     * 处理用户名或密码错误的异常
     */
    @ExceptionHandler({BadCredentialsException.class})
    public Result badCredentialsException(BadCredentialsException e) {
        return Result.of(ResultCodeEnum.BAD_REQUEST_ERROR, e.getMessage());
    }

}
