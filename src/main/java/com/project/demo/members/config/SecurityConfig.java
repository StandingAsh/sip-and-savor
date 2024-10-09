package com.project.demo.members.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // CSRF 인증 해제
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement((session) ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // URL 별 접근 권한 부여
        http.authorizeHttpRequests((requests) ->
                requests.requestMatchers("/", "/members/**", "/mypage")
                        .permitAll()
                        .anyRequest().authenticated());

        // 로그인 설정
        http.formLogin((auth) -> auth.loginPage("/members/login")
                .loginProcessingUrl("/members/loginProc")
                .defaultSuccessUrl("/mypage")
                .usernameParameter("userId")
                .passwordParameter("password")
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
