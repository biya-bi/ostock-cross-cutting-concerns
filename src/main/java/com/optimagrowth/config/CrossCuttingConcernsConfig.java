package com.optimagrowth.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {
        ServletContextConfig.class,
        UserContextConfig.class
})
public class CrossCuttingConcernsConfig {
}
