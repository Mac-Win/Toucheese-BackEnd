package com.toucheese.image.util;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.toucheese.global.config.S3Config;
import jakarta.servlet.ServletInputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class S3ImageUtil {

    private final S3Config s3Config;

    @Value("${cloud.aws.bucket-name}")
    private String bucketName;

    @Value("${cloud.aws.upload-path}")
    private String uploadPath;

    public void uploadImage(ObjectMetadata metadata, String filename, ServletInputStream stream) {
        s3Config.amazonS3Client()
                .putObject(
                        bucketName + uploadPath,
                        filename,
                        stream,
                        metadata
                );
    }

}
