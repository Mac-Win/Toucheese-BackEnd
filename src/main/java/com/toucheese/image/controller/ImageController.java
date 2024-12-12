package com.toucheese.image.controller;

import com.toucheese.image.service.ImageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/images")
public class ImageController {

    private final ImageService imageService;

    /**
     * Stream 방식으로 이미지 업로드
     * @param request 요청 정보 (InputStream, Metadata)
     * @param filename 파일이름
     */
    @PostMapping
    public void streamImageUpload(
            HttpServletRequest request,
            @RequestParam String filename
    ) {
        imageService.streamImageUpload(request, filename);
    }
}
