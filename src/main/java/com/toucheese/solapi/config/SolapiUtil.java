package com.toucheese.solapi.config;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SolapiUtil {
    private final DefaultMessageService solapiService;

    public SolapiUtil(
            @Value("${solapi.api-key}") String apiKey,
            @Value("${solapi.api-secret-key}") String apiSecretKey,
            @Value("${solapi.base-url}") String baseUrl
    ) {
        this.solapiService = NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey, baseUrl);
    }

    public DefaultMessageService getSolapiService() {
        return solapiService;
    }
}
