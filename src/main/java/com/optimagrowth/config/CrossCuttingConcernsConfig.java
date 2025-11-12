package com.optimagrowth.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@AutoConfiguration
@ComponentScan(basePackageClasses = {
        ServletContextConfig.class,
        UserContextConfig.class
})
public class CrossCuttingConcernsConfig {
}
