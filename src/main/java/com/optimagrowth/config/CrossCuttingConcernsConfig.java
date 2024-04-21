package com.optimagrowth.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.optimagrowth.filter.UserContextFilter;
import com.optimagrowth.interceptor.UserContextInterceptor;

@Configuration
public class CrossCuttingConcernsConfig {

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(UserContextInterceptor userContextInterceptor) {
        var template = new RestTemplate();

        var interceptors = template.getInterceptors();
        interceptors.add(userContextInterceptor);

        return template;
    }

    @Bean
    public UserContextFilter userContextFilter() {
        return new UserContextFilter();
    }

    @Bean
    public UserContextInterceptor userContextInterceptor() {
        return new UserContextInterceptor();
    }

}
