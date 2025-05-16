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
                .cors() // Activează suportul pentru CORS, dacă este necesar
                .and()
                .csrf().disable() // Dezactivează CSRF pentru a permite cereri neautentificate în timpul testării
                .authorizeHttpRequests()
                // Permite accesul liber la rutele și resursele statice:
                .requestMatchers("/login", "/register", "/oaspeti/**", "/rezervari", "/rezervari/**").permitAll()
                .requestMatchers("/css/**", "/js/**", "/img/**", "/webjars/**","/static").permitAll() // Încarcă fișierele statice
                .requestMatchers("/user/**").hasAuthority("user") // Protejează resursele specifice
                .anyRequest().authenticated() // Orice altă resursă necesită autentificare
                .and()
                .formLogin()
                .loginPage("/login") // Pagină personalizată de login
                .defaultSuccessUrl("/rezervari", true) // Redirecționează utilizatorii după login
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout") // URL-ul pentru logout
                .logoutSuccessUrl("/login?logout") // Redirecționează după logout
                .permitAll()
                .and()
                .build();
    }

    @Bean
    public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

}
