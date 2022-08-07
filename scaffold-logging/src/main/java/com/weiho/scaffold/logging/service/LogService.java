package com.weiho.scaffold.logging.service;

import com.weiho.scaffold.logging.annotation.Logging;
import com.weiho.scaffold.logging.entity.Log;
import com.weiho.scaffold.mp.service.CommonService;
import org.aspectj.lang.JoinPoint;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Weiho
 * @since 2022-08-06
 */
public interface LogService extends CommonService<Log> {
    /**
     * 存储日志信息
     *
     * @param joinPoint  AOP切点对象
     * @param request    请求对象
     * @param logging    注解
     * @param log        Log对象
     * @param e          异常
     * @param jsonResult 响应结果
     */

    @Async
    //放入异步线程池运行
    void saveLogInfo(final JoinPoint joinPoint, HttpServletRequest request, Logging logging, Log log, final Exception e, Object jsonResult);
}
