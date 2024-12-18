package com.toucheese.image.controller;

import com.toucheese.image.entity.ImageType;
import com.toucheese.image.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "이미지 업로드를 위한 API", description = "기존 이미지 업로드, 새 이미지 업로드")
public class ImageController {

    private final ImageService imageService;

    /**
     * Stream 방식으로 이미지 업로드 (기존 이미지 업로드용)
     * @param request 요청 정보 (InputStream, Metadata)
     * @param filename 파일이름
     */
    @PostMapping("/v1/images")
    @Operation(summary = "기존 이미지 업로드만을 위한 API", description = "이미 DB에 적재되어 있는 이미지를 업로드만 진행")
    public void streamExistingImageUpload(
            HttpServletRequest request,
            @RequestParam String filename
    ) {
        imageService.uploadExistingImage(request, filename);
    }

    /**
     * Stream 방식으로 스튜디오 이미지 업로드
     * @param request 요청 정보 (InputStream, Metadata)
     * @param filename 파일이름
     * @param studioId 스튜디오 ID
     */
    @PostMapping("/v2/studios/images")
    @Operation(summary = "스튜디오 이미지 업로드를 위한 API", description = "이미지 업로드 및 DB 내 데이터 적재")
    public void streamStudioImageUpload(
            HttpServletRequest request, @RequestParam String filename, @RequestParam Long studioId
    ) {
        imageService.uploadImageWithDetails(request, filename, studioId, ImageType.STUDIO);
    }

    /**
     * Stream 방식으로 리뷰 이미지 업로드
     * @param request 요청 정보 (InputStream, Metadata)
     * @param filename 파일이름
     * @param reviewId 리뷰 ID
     */
    @PostMapping("/v2/reviews/images")
    @Operation(summary = "리뷰 이미지 업로드를 위한 API", description = "이미지 업로드 및 DB 내 데이터 적재")
    public void streamReviewImageUpload(
            HttpServletRequest request, @RequestParam String filename, @RequestParam Long reviewId
    ) {
        imageService.uploadImageWithDetails(request, filename, reviewId, ImageType.REVIEW);
    }

    /**
     * Stream 방식으로 리뷰 이미지 업로드
     * @param request 요청 정보 (InputStream, Metadata)
     * @param filename 파일이름
     * @param studioId 스튜디오 ID
     */
    @PostMapping("/v2/facilities/images")
    @Operation(summary = "시설 이미지 업로드를 위한 API", description = "이미지 업로드 및 DB 내 데이터 적재")
    public void streamFacilityImageUpload(
            HttpServletRequest request, @RequestParam String filename, @RequestParam Long studioId
    ) {
        imageService.uploadImageWithDetails(request, filename, studioId, ImageType.FACILITY);
    }
}
