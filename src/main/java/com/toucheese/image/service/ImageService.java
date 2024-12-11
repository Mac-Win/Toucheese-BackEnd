package com.toucheese.image.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.toucheese.global.exception.ToucheeseInternalServerErrorException;
import com.toucheese.image.repository.StudioImageRepository;
import com.toucheese.image.util.S3ImageUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final S3ImageUtil s3ImageUtil;
    private final StudioImageRepository studioImageRepository;

    /**
     * Stream 방식으로 이미지 업로드 하기 위한 메서드
     * @param request 요청 정보 (InputStream, Metadata)
     * @param filename 파일이름
     */
    public void streamImageUpload(HttpServletRequest request, String filename) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(request.getContentType());
        metadata.setContentLength(request.getContentLength());

        try {
            s3ImageUtil.uploadImage(metadata, filename, request.getInputStream());
        } catch (IOException e) {
            throw new ToucheeseInternalServerErrorException();
        }
    }
}
