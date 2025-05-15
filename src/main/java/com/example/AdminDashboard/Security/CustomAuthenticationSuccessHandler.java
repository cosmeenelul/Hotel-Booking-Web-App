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
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("admin"))) {
            response.sendRedirect("/rezervari/toateRezervarile"); // Redirecționează admin-ul
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("user"))) {
            response.sendRedirect("/user/rezervarileMele"); // Redirecționează user-ul
        } else {
            response.sendRedirect("/login?error"); // În caz de rol necunoscut, întoarce-l la login
        }
    }
}