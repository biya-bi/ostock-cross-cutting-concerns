package com.optimagrowth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.optimagrowth.authentication.ProblemDetailAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationEntryPoint entryPoint,
            @Nullable @Value("${ostock.api.authentication.allowedEndpoints:#{null}}") String[] allowedEndpoints)
            throws Exception {
        return httpSecurity
                .authorizeHttpRequests(registry -> authenticated(registry, allowedEndpoints))
                .oauth2ResourceServer(configurer -> configurer.authenticationEntryPoint(entryPoint)
                        .jwt(Customizer.withDefaults()))
                .build();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(ObjectMapper mapper) {
        return new ProblemDetailAuthenticationEntryPoint(mapper);
    }

    private AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authenticated(
            AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry,
            String[] allowedEndpoints) {
        var matcherRegistry = allowedEndpoints != null
                ? registry.requestMatchers(allowedEndpoints).permitAll()
                : registry;

        return matcherRegistry.anyRequest().authenticated();
    }

}
