package com.project.libraryManagementSystem.configurations;

import com.project.libraryManagementSystem.utils.InputValidation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ErrorConfig {

    @Bean
    public InputValidation inputDetails() {
        return new InputValidation();
    }
}
