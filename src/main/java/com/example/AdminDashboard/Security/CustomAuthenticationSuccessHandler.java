package com.example.AdminDashboard.Security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        boolean isAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With")) || request.getHeader("Postman-Token") != null;

        if (isAjax) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            String mesaj;

            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("admin"))) {
                mesaj = "{\"message\":\"login success\", \"redirect\":\"/admin/index\"}";
            } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("user"))) {
                mesaj = "{\"message\":\"login success\", \"redirect\":\"/user/index\"}";
            } else {
                mesaj = "{\"message\":\"unknown role\"}";
            }

            response.getWriter().write(mesaj);
        } else {
            // Varianta pentru browser
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("admin"))) {
                response.sendRedirect("/admin/index");
            } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("user"))) {
                response.sendRedirect("/user/index");
            } else {
                response.sendRedirect("/login?error");
            }
        }
    }

}