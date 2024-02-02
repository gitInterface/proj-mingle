package com.ispan.mingle.projmingle.config;

import java.io.IOException;

import jakarta.servlet.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@WebFilter(filterName = "CorsFilter",urlPatterns = "/*")
@Configuration
@Component
public class CorsConfigFilter implements Filter {

    @Value("${front.end.host}")
    private String front;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        // addAllowedOrigin 不能設定為* 因為與 allowCredential 衝突
        response.setHeader("Access-Control-Allow-Origin",front);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH, DELETE, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, sessionToken");
        chain.doFilter(req, res);
    }
}
