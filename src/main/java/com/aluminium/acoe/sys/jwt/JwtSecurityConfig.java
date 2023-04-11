package com.aluminium.acoe.sys.jwt;

import com.aluminium.acoe.common.exception.JwtExceptionFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final TokenProvider tokenProvider;
    private final JwtExceptionFilter jwtExceptionFilter;

    /**
     * SecurityConfigurerAdapter를 extends하고 TokenProvide를 주입받아 JwtFilter를 통해 Security로직에 필터를 등록
     */
    @Override
    public void configure(HttpSecurity http) {
        http.addFilterBefore(
                new JwtFilter(tokenProvider),
                UsernamePasswordAuthenticationFilter.class
        );
        http.addFilterBefore(jwtExceptionFilter, JwtFilter.class);
    }
}