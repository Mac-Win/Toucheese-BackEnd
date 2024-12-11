package com.toucheese.global.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ImageConfig {

    @Value("${secret.image-base-url}")
    private String imageBaseUrl;

}