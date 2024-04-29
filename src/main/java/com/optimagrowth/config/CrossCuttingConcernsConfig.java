package com.optimagrowth.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {
        MessageConfig.class,
        SecurityConfig.class,
        ServletContextConfig.class,
        UserContextConfig.class
})
public class CrossCuttingConcernsConfig {
}
