package com.toucheese.image.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.toucheese.image.entity.ImageType;
import com.toucheese.image.service.ImageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

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
    public void existingImageStreamUpload(
            HttpServletRequest request,
            @RequestParam String filename
    ) {
        imageService.uploadExistingImage(request, filename);
    }

    /**
     * MultipartFile 방식으로 스튜디오 이미지 업로드
     * @param uploadFiles 업로드 요청 파일 목록
     * @param studioId 스튜디오 ID
     */
    @PostMapping("/v2/studios/{studioId}/images")
    @Operation(summary = "스튜디오 이미지 업로드를 위한 API", description = "이미지 업로드 및 DB 내 데이터 적재")
    public void studioImageUpload(
            @RequestPart List<MultipartFile> uploadFiles,
            @PathVariable Long studioId
    ) {
        imageService.uploadImageWithDetails(uploadFiles, studioId, ImageType.STUDIO);
    }
    /**

     * MultipartFile 방식으로 리뷰 이미지 업로드
     * @param uploadFiles 업로드 요청 파일 목록
     * @param reviewId 리뷰 ID
     */
    @PostMapping("/v2/reviews/{reviewId}/images")
    @Operation(summary = "리뷰 이미지 업로드를 위한 API", description = "이미지 업로드 및 DB 내 데이터 적재")
    public void reviewImageUpload(
            @RequestPart List<MultipartFile> uploadFiles,
            @PathVariable Long reviewId
    ) {
        imageService.uploadImageWithDetails(uploadFiles, reviewId, ImageType.REVIEW);
    }

    /**
     * MultipartFile 방식으로 리뷰 이미지 업로드
     * @param uploadFiles 업로드 요청 파일 목록
     * @param studioId 스튜디오 ID
     */
    @PostMapping("/v2/facilities/{studioId}/images")
    @Operation(summary = "시설 이미지 업로드를 위한 API", description = "이미지 업로드 및 DB 내 데이터 적재")
    public void facilityImageUpload(
            @RequestPart List<MultipartFile> uploadFiles,
            @PathVariable Long studioId
    ) {
        imageService.uploadImageWithDetails(uploadFiles, studioId, ImageType.FACILITY);
    }

    /**
     * MultipartFile 방식으로 리뷰 이미지 업로드
     * @param uploadFiles 업로드 요청 파일 목록
     * @param questionId 문의 ID
     */
    @PostMapping("/v2/questions/{questionId}/images")
    @Operation(summary = "문의 이미지 업로드를 위한 API", description = "이미지 업로드 및 DB 내 데이터 적재")
    public void questionImageUpload(
            @RequestPart List<MultipartFile> uploadFiles,
            @PathVariable Long questionId
    ) {
        imageService.uploadImageWithDetails(uploadFiles, questionId, ImageType.QUESTION);
    }
}
