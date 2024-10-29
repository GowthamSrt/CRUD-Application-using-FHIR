package com.ideas2it.patient.config;

import ca.uhn.fhir.context.FhirContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class fhirConfig {

    @Bean
    public FhirContext fhirContext() {
        return FhirContext.forR4();
    }
}
