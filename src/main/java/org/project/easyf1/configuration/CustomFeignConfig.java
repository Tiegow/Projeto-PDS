package org.project.easyf1.configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomFeignConfig {

    @Value("${rapidapi.key}")
    private String apiKey;

    @Bean
    public RequestInterceptor cleanHeadersInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {

                template.headers(null);

//                template.header("x-rapidapi-key", apiKey);
                template.header("x-apisports-key", apiKey);
            }
        };
    }
}
