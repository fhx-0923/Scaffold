package com.weiho.scaffold.logging.aspect;

import com.weiho.scaffold.common.util.ip.IpUtils;
import com.weiho.scaffold.common.util.security.SecurityUtils;
import com.weiho.scaffold.logging.annotation.Logging;
import com.weiho.scaffold.logging.entity.Log;
import com.weiho.scaffold.logging.enums.LogTypeEnum;
import com.weiho.scaffold.logging.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Weiho
 * @date 2022/8/7
 */
@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Autowired
    private LogService logService;

    private final ThreadLocal<Long> currentTime = new ThreadLocal<>();

    private Log logInfo;

    @Before(value = "@annotation(logging)")
    public void deBefore(Logging logging) {
        logInfo = new Log();
        log.info("Log -> 日志对象创建");
    }

    @Around(value = "@annotation(logging)")
    public Object doAround(ProceedingJoinPoint joinPoint, Logging logging) throws Throwable {
        Object result;
        currentTime.set(System.currentTimeMillis());
        result = joinPoint.proceed();
        logInfo.setTime(System.currentTimeMillis() - currentTime.get());
        return result;
    }

    @AfterReturning(pointcut = "@annotation(logging)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Logging logging, Object jsonResult) {
        logInfo.setLogType(LogTypeEnum.INFO.getMsg());
        //设置操作用户
        logInfo.setUsername(getUsername());
        //获取HttpServletRequest对象
        HttpServletRequest request = IpUtils.getHttpServletRequest();
        logInfo.setRequestMethod(request.getMethod());
        logInfo.setRequestUrl(request.getRequestURI());
        logService.saveLogInfo(joinPoint, request, logging, logInfo, null, jsonResult);
    }

    @AfterThrowing(value = "@annotation(logging)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Logging logging, Exception e) {
        logInfo.setLogType(LogTypeEnum.ERROR.getMsg());
        //设置操作用户
        logInfo.setUsername(getUsername());
        //获取HttpServletRequest对象
        HttpServletRequest request = IpUtils.getHttpServletRequest();
        logInfo.setRequestMethod(request.getMethod());
        logInfo.setRequestUrl(request.getRequestURI());
        logService.saveLogInfo(joinPoint, request, logging, logInfo, e, null);
    }

    /**
     * 获取当前登录用户的用户名
     *
     * @return 用户名
     */
    public String getUsername() {
        try {
            return SecurityUtils.getUsername();
        } catch (Exception e) {
            return "";
        }
    }
}
