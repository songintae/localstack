package com.example.localstack;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocalStackConfiguration {

    @Bean
    public LocalStackContainer localStackContainer() {
        return new LocalStackContainer();
    }
}
