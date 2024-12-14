package com.toucheese.global.config;

import com.toucheese.image.util.S3ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ImageConfig {

    private final S3ImageUtil s3ImageUtil;

    @Value("${secret.image-base-url}")
    private String imageBaseUrl;

    public String getSourceImageBaseUrl() {
        return imageBaseUrl + s3ImageUtil.getUploadPath();
    }

    public String getResizedImageBaseUrl() {
        return imageBaseUrl + s3ImageUtil.getResizedPath();
    }
}