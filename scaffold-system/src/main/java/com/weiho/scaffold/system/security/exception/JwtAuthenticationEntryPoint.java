package com.weiho.scaffold.system.security.exception;

import com.weiho.scaffold.common.util.response.ExceptionResponseUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 当用户尝试访问安全的REST资源而不提供任何凭据时，将调用此方法
 *
 * @author Weiho
 * @date 2022/7/29
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        ExceptionResponseUtils.sendResponse(response, "未提供任何凭据");
    }
}
