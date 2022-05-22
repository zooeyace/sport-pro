package com.zyy.sport.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zyy.sport.common.R;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 接口访问没有权限时
 */

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(403);
        PrintWriter printWriter = response.getWriter();
        printWriter.write(new ObjectMapper().writeValueAsString(R.errorOf("权限不足，无法访问")));
        printWriter.flush();
        printWriter.close();
    }
}