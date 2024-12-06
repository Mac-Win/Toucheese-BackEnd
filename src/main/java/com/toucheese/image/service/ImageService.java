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
