package com.toucheese.image.util;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.toucheese.global.config.S3Config;
import jakarta.servlet.ServletInputStream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component @Getter
@RequiredArgsConstructor
public class S3ImageUtil {

    private final S3Config s3Config;

    @Value("${cloud.aws.bucket-name}")
    private String bucketName;

    @Value("${cloud.aws.upload-path}")
    private String uploadPath;

    @Value("${cloud.aws.resized-path}")
    private String resizedPath;

    /**
     * S3에 이미지 업로드
     * @param metadata 요청 메타데이터
     * @param filename 파일 이름
     * @param stream InputStream (파일 전송)
     */
    public void uploadImage(String filename, InputStream stream, ObjectMetadata metadata) {
        s3Config.amazonS3Client()
                .putObject(createPutObjectRequest(filename, stream, metadata));
    }

    public PutObjectRequest createPutObjectRequest(String filename, InputStream stream, ObjectMetadata metadata) {
        return new PutObjectRequest(
                bucketName + uploadPath,
                filename,
                stream,
                metadata
        );
    }

}
