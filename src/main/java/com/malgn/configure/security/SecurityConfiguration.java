package com.malgn.configure.security;

import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request -> request
                .requestMatchers("/members/login", "/members/signup", "/h2-console/**", "/actuator/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/contents", "/contents/*").permitAll()
                .requestMatchers(HttpMethod.POST, "/contents").authenticated()
                .requestMatchers(HttpMethod.PATCH, "/contents/*").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/contents/*").authenticated()
                .anyRequest().authenticated()
        );

        http.exceptionHandling(exception -> exception
                .authenticationEntryPoint((request, response, authException) ->
                        writeErrorResponse(response, HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."))
                .accessDeniedHandler((request, response, accessDeniedException) ->
                        writeErrorResponse(response, HttpStatus.FORBIDDEN, "접근 권한이 없습니다."))
        );

        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private void writeErrorResponse(HttpServletResponse response, HttpStatus status, String message) throws java.io.IOException {
        response.setStatus(status.value());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(
                "{\"status\":" + status.value() + ",\"message\":\"" + message + "\"}"
        );
    }
}
