package com.toucheese.global.config;

import java.util.Base64;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class AppConfig {

    private final byte[] secretKey;

    public AppConfig(
            @Value("${jwt.secret-key}")
            String secretKey
    ) {
        this.secretKey = Base64.getDecoder().decode(secretKey);
    }
}