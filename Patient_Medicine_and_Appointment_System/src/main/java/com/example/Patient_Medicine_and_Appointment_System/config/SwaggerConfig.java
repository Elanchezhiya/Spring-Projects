package com.example.Patient_Medicine_and_Appointment_System.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI patientManagementOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Patient Management API")
                        .description("API Documentation for Patient Management System")
                        .version("1.0.0"));
    }
}
