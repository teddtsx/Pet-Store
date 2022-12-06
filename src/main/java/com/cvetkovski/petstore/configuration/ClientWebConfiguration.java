package com.cvetkovski.petstore.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class ClientWebConfiguration implements WebMvcConfigurer {

    @Bean
    public MethodValidationPostProcessor methodValidatorPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}
