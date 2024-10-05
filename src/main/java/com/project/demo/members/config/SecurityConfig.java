package com.project.demo.members.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // URL 별 접근 권한 부여
        http.authorizeRequests((auth) -> auth
                .requestMatchers("/", "/members/new", "/members/login")
                .permitAll()
        );

        // 로그인 설정
        http.formLogin((auth) -> auth.loginPage("/members/login")
                .loginProcessingUrl("/members/loginProc")
                .permitAll()
        );

        // 로그아웃 설정
        http.logout((auth) -> auth.logoutUrl("/members/logout"));

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
