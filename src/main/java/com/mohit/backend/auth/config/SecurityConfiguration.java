package com.mohit.backend.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mohit.backend.auth.service.AuthFilterService;
import com.mysql.cj.protocol.AuthenticationProvider;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {
    @Autowired
    private AuthFilterService authFilterService;
    @Autowired
    private AuthenticationProvider authprovider;

   @Bean 
   public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
    http.csrf(customizer->customizer.disable())
    .authorizeHttpRequests(auth->auth
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/passwd/**").permitAll()
                        .anyRequest().authenticated())
    .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    .addFilterBefore(authFilterService, UsernamePasswordAuthenticationFilter.class);

    return http.build();

   }
}
