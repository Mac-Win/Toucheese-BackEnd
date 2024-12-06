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

    @PostMapping
    public void streamImageUpload(
            HttpServletRequest request,
            @RequestParam String filename
    ) {
        imageService.streamImageUpload(request, filename);
    }
}
