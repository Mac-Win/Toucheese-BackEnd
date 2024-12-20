package com.toucheese.image.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.toucheese.global.exception.ToucheeseInternalServerErrorException;
import com.toucheese.image.entity.*;
import com.toucheese.image.repository.FacilityImageRepository;
import com.toucheese.image.repository.ReviewImageRepository;
import com.toucheese.image.repository.StudioImageRepository;
import com.toucheese.image.util.S3ImageUtil;
import com.toucheese.review.entity.Review;
import com.toucheese.review.service.ReviewService;
import com.toucheese.studio.entity.Studio;
import com.toucheese.studio.service.StudioService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.toucheese.image.util.FilenameUtil.buildFilePath;
import static com.toucheese.image.util.FilenameUtil.extractFileExtension;
import static com.toucheese.image.util.MetadataUtil.createMetadata;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final S3ImageUtil s3ImageUtil;

    private final StudioService studioService;
    private final StudioImageRepository studioImageRepository;

    private final ReviewService reviewService;
    private final ReviewImageRepository reviewImageRepository;

    private final FacilityImageRepository facilityImageRepository;

    private final ImageInfoService imageInfoService;

    private static final String RESIZED_EXTENSION = ".webp";

    /**
     * 기존 이미지를 업로드하기 위한 메서드
     * @param request 요청 정보 (InputStream, Metadata)
     * @param filename 파일 이름
     */
    public void uploadExistingImage(HttpServletRequest request, String filename) {
        uploadImage(request, filename, createMetadata(request));
    }

    /**
     * 새 이미지 업로드하기 위한 메서드
     * @param uploadFiles 업로드 할 파일 목록
     * @param entityId 연관관계 객체 아이디
     * @param imageType 이미지 타입
     */
    @Transactional
    public void uploadImageWithDetails(List<MultipartFile> uploadFiles, Long entityId, ImageType imageType) {
        for (MultipartFile uploadFile : uploadFiles) {
            String filename = uploadFile.getOriginalFilename();
            ImageInfo imageInfo = imageInfoService.createImageInfo(filename);
            String extension = extractFileExtension(Objects.requireNonNull(filename));

            uploadImage(uploadFile, imageInfo.getUploadFilename() + extension, createMetadata(uploadFile));

            switch (imageType) {
                case STUDIO -> saveStudioImage(entityId, imageInfo, extension);
                case REVIEW -> saveReviewImage(entityId, imageInfo, extension);
                case FACILITY -> saveFacilityImage(entityId, imageInfo, extension);
                default -> throw new IllegalArgumentException("Unsupported image type: " + imageType);
            }
        }
    }

    private void saveStudioImage(Long studioId, ImageInfo imageInfo, String extension) {
        Studio studio = studioService.findStudioById(studioId);
        StudioImage studioImage = StudioImage.builder()
                .studio(studio)
                .originalPath(buildFilePath(imageInfo.getUploadFilename(), extension))
                .resizedPath(buildFilePath(imageInfo.getUploadFilename(), RESIZED_EXTENSION))
                .imageInfo(imageInfo)
                .build();
        studioImageRepository.save(studioImage);
    }

    private void saveReviewImage(Long reviewId, ImageInfo imageInfo, String extension) {
        Review review = reviewService.findReviewById(reviewId);
        ReviewImage reviewImage = ReviewImage.builder()
                .review(review)
                .originalPath(buildFilePath(imageInfo.getUploadFilename(), extension))
                .resizedPath(buildFilePath(imageInfo.getUploadFilename(), RESIZED_EXTENSION))
                .imageInfo(imageInfo)
                .build();
        reviewImageRepository.save(reviewImage);
    }

    private void saveFacilityImage(Long studioId, ImageInfo imageInfo, String extension) {
        Studio studio = studioService.findStudioById(studioId);
        FacilityImage facilityImage = FacilityImage.builder()
                .studio(studio)
                .originalPath(buildFilePath(imageInfo.getUploadFilename(), extension))
                .resizedPath(buildFilePath(imageInfo.getUploadFilename(), RESIZED_EXTENSION))
                .imageInfo(imageInfo)
                .build();
        facilityImageRepository.save(facilityImage);
    }

    /**
     * 요청받은 이미지 업로드
     * @param request 요청 정보 (InputStream, Metadata)
     * @param filename 업로드 할 파일 이름
     */
    private void uploadImage(HttpServletRequest request, String filename, ObjectMetadata metadata) {
        try {
            s3ImageUtil.uploadImage(filename, request.getInputStream(), metadata);
        } catch (IOException e) {
            throw new ToucheeseInternalServerErrorException(e.getMessage());
        }
    }

    /**
     * 요청받은 이미지 업로드
     * @param uploadFile 업로드 요청 파일
     * @param filename 생성된 파일 이름
     * @param metadata 생성된 메타데이터
     */
    private void uploadImage(MultipartFile uploadFile, String filename, ObjectMetadata metadata) {
        try {
            s3ImageUtil.uploadImage(filename, uploadFile.getInputStream(), metadata);
        } catch (IOException e) {
            throw new ToucheeseInternalServerErrorException(e.getMessage());
        }
    }
}
