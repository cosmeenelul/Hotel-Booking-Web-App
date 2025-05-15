package com.example.AdminDashboard.Security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class OaspeteRegistrationSecurityConfig {


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors()
                .and()
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/login", "/register/**").permitAll() // permisiune pentru rute publice
                .requestMatchers("/user/**").hasAuthority("user")      // protejează rutele /user/*
                .anyRequest().hasAuthority("admin")                   // protejează restul numai pentru admin
                .and()
                .formLogin()
                .loginPage("/login")                              // pagină personalizată de login
                .successHandler(customAuthenticationSuccessHandler()) // setează successHandler-ul personalizat
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")                // redirecționare după logout
                .permitAll()
                .and()
                .build();
    }

    @Bean
    public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }
}
