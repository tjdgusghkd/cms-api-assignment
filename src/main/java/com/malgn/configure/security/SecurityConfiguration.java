package com.malgn.configure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	 @Bean
	  public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
	      http.authorizeHttpRequests(request -> request
	              .requestMatchers("/members/login", "/h2-console/**", "/actuator/**", "/members/signup").permitAll()
	              .requestMatchers(HttpMethod.GET, "/contents", "/contents/*").permitAll()
	              .requestMatchers(HttpMethod.POST, "/contents").authenticated()
	              .requestMatchers(HttpMethod.PATCH, "/contents/*").authenticated()
	              .requestMatchers(HttpMethod.DELETE, "/contents/*").authenticated()
	              .anyRequest().authenticated()
	      );

	      http.csrf(AbstractHttpConfigurer::disable);
	      http.cors(AbstractHttpConfigurer::disable);

	      return http.build();
	  }
    
    // BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }

}
