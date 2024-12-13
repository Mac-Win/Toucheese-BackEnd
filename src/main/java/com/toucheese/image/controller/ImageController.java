package com.toucheese.image.controller;

import com.toucheese.image.service.ImageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    /**
     * Stream 방식으로 이미지 업로드 (기존 이미지 업데이트용)
     * @param request 요청 정보 (InputStream, Metadata)
     * @param filename 파일이름
     */
    @PostMapping("/v1/images")
    public void streamExistingImageUpload(
            HttpServletRequest request,
            @RequestParam String filename
    ) {
        imageService.uploadExistingImage(request, filename);
    }

    /**
     * Stream 방식으로 이미지 업로드 (새 이미지 업로드용)
     * @param request 요청 정보 (InputStream, Metadata)
     * @param filename 파일이름
     */
    @PostMapping("/v2/images")
    public void streamImageUpload(
            HttpServletRequest request,
            @RequestParam String filename,
            @RequestParam Long studioId
    ) {
        imageService.uploadNewImage(request, filename, studioId);
    }
}
