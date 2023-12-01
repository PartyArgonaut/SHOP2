package com.shop.config;


import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint { //AuthenticationEntryPoint는 인증에 실패한 경우 어떻게 응답할지를 정의하는 인터페이스입니다. commence 메서드는 실제로 인증이 실패했을 때 호출되는 메서드입니다.
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if("XMLHttpRequest".equals(request.getHeader("x-requested-with"))){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        } else {
            response.sendRedirect("/members/login");
        }
    }
}
