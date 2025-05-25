package org.project.easyf1.configuration;

import feign.jackson.JacksonEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeminiConfiguration {

    @Bean
    public JacksonEncoder feignEncoder() {
        return new JacksonEncoder();
    }
}
