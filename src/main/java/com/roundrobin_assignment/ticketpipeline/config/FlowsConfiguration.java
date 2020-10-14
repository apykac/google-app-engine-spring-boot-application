package com.roundrobin_assignment.ticketpipeline.config;

import com.roundrobin_assignment.ticketpipeline.config.properties.FlowsProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlowsConfiguration {
    @Bean
    @ConfigurationProperties("app.flow")
    public FlowsProperties flowsProperties() {
        return new FlowsProperties();
    }
}
