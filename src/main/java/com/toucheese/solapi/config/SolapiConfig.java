package com.toucheese.solapi.config;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SolapiConfig {
    @Bean
    public DefaultMessageService solapiService(
            @Value("${solapi.api-key}") String apiKey,
            @Value("${solapi.api-secret-key}") String apiSecretKey,
            @Value("${solapi.base-url}") String baseUrl
    ) {
        return NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey, baseUrl);
    }
}
