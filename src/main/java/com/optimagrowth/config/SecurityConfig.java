package com.optimagrowth.config;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerAuthenticationManagerResolver;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.optimagrowth.authentication.ProblemDetailAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationEntryPoint entryPoint,
            JwtConfig jwtConfig,
            AuthorizationConfig authorizationConfig)
            throws Exception {
        return httpSecurity
                .authorizeHttpRequests(registry -> authenticated(registry, authorizationConfig))
                .oauth2ResourceServer(configurer -> configure(entryPoint, configurer, jwtConfig))
                .build();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(ObjectMapper mapper) {
        return new ProblemDetailAuthenticationEntryPoint(mapper);
    }

    private AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authenticated(
            AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry,
            AuthorizationConfig authorizationConfig) {
        var allowedEndpoints = authorizationConfig.getAllowedEndpoints();
        var matcherRegistry = ObjectUtils.isNotEmpty(allowedEndpoints)
                ? registry.requestMatchers(allowedEndpoints).permitAll()
                : registry;

        return matcherRegistry.anyRequest().authenticated();
    }

    private OAuth2ResourceServerConfigurer<HttpSecurity> configure(AuthenticationEntryPoint entryPoint,
            OAuth2ResourceServerConfigurer<HttpSecurity> configurer, JwtConfig jwtConfig) {
        var resolver = JwtIssuerAuthenticationManagerResolver
                .fromTrustedIssuers(jwtConfig.getGoogleIssuerUri(), jwtConfig.getKeycloakIssuerUri());
        return configurer.authenticationEntryPoint(entryPoint).authenticationManagerResolver(resolver);
    }


}
