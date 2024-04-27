package com.optimagrowth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.optimagrowth.authentication.ProblemDetailAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationEntryPoint entryPoint)
            throws Exception {
        httpSecurity.authorizeHttpRequests(customizer -> customizer.anyRequest().authenticated())
                .oauth2ResourceServer(customizer -> customizer.authenticationEntryPoint(entryPoint)
                        .jwt(Customizer.withDefaults()));
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(ObjectMapper mapper) {
        return new ProblemDetailAuthenticationEntryPoint(mapper);
    }

}
