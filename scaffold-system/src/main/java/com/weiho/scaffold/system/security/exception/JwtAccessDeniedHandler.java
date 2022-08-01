package com.weiho.scaffold.system.security.exception;

import com.weiho.scaffold.common.util.response.ExceptionResponseUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 当用户在没有授权的情况下访问受保护的REST资源时，将调用此方法
 *
 * @author Weiho
 * @date 2022/7/29
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ExceptionResponseUtils.sendResponse(response, "访问受限资源时没有得到许可");
    }
}
