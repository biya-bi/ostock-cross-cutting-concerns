package com.optimagrowth.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = { MessageConfig.class, SecurityConfig.class, UserContextConfig.class })
public class CrossCuttingConcernsConfig {
}
