package com.optimagrowth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "security.authorization")
public class AuthorizationConfig {
    private String[] allowedEndpoints;
}
