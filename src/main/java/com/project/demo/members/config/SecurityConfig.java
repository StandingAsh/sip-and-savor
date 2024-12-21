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

    private final String[] openURLs = {
            "/", "/members/**", "/whiskeys", "/whiskeys/**", "/crawling"
    };

    private final String[] userOnlyURLs = {
            "/mypage", "mypage/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // CSRF 인증 해제
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement((session) ->
                session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));

        // URL 별 접근 권한 부여
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers(userOnlyURLs).hasRole("USER")
                .requestMatchers(openURLs).permitAll()
                .anyRequest().authenticated()
        );

        // 로그인 설정
        http.formLogin((auth) -> auth.loginPage("/members/login")
                .loginProcessingUrl("/members/loginProc")
                .defaultSuccessUrl("/mypage")
                .usernameParameter("userId")
                .passwordParameter("password")
                .failureHandler(new LoginFailureHandler())
                .permitAll()
        );

        // 로그아웃 설정
        http.logout((auth) -> auth
                .logoutUrl("/members/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
        );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
