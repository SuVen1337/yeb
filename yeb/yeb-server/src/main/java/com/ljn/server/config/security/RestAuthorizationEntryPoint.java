package com.ljn.server.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljn.server.pojo.res.RespBean;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: ljn
 * @Date: 2022/02/19/19:27
 * @Description: 当未登录或token失效时，自定义的返回结果
 */
@Component
public class RestAuthorizationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            RespBean bean = RespBean.error("请重新登录");
            bean.setCode(401);
            out.write(new ObjectMapper().writeValueAsString(bean));
            out.flush();
            out.close();

    }
}
