package org.project.framework.configuration;

import feign.jackson.JacksonEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LLMConfiguration {

    @Bean
    public JacksonEncoder feignEncoder() {
        return new JacksonEncoder();
    }
}
