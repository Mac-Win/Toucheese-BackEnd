package com.toucheese.image.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.toucheese.global.exception.ToucheeseInternalServerErrorException;
import com.toucheese.image.entity.FacilityImage;
import com.toucheese.image.entity.ImageType;
import com.toucheese.image.entity.ReviewImage;
import com.toucheese.image.entity.StudioImage;
import com.toucheese.image.repository.FacilityImageRepository;
import com.toucheese.image.repository.ReviewImageRepository;
import com.toucheese.image.repository.StudioImageRepository;
import com.toucheese.image.util.FilenameUtil;
import com.toucheese.image.util.S3ImageUtil;
import com.toucheese.review.entity.Review;
import com.toucheese.review.service.ReviewService;
import com.toucheese.studio.entity.Studio;
import com.toucheese.studio.service.StudioService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.function.BiFunction;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final S3ImageUtil s3ImageUtil;
    private final FilenameUtil filenameUtil;

    private final StudioService studioService;
    private final StudioImageRepository studioImageRepository;

    private final ReviewService reviewService;
    private final ReviewImageRepository reviewImageRepository;

    private final FacilityImageRepository facilityImageRepository;

    private static final String RESIZED_EXTENSION = ".webp";

    /**
     * 기존 이미지를 업로드하기 위한 메서드
     * @param request 요청 정보 (InputStream, Metadata)
     * @param filename 파일 이름
     */
    public void uploadExistingImage(HttpServletRequest request, String filename) {
        uploadImage(request, filename);
    }

    @Transactional
    public void uploadImageWithDetails(HttpServletRequest request, String filename, Long entityId, ImageType imageType) {
        String randomFilename = generateRandomFilename(imageType);
        String extension = filenameUtil.extractFileExtension(filename);
        uploadImage(request, randomFilename + extension);

        switch (imageType) {
            case STUDIO -> saveStudioImage(entityId, filename, randomFilename, extension);
            case REVIEW -> saveReviewImage(entityId, filename, randomFilename, extension);
            case FACILITY -> saveFacilityImage(entityId, filename, randomFilename, extension);
            default -> throw new IllegalArgumentException("Unsupported image type: " + imageType);
        }
    }

    private void saveStudioImage(Long studioId, String filename, String randomFilename, String extension) {
        Studio studio = studioService.findStudioById(studioId);
        StudioImage studioImage = StudioImage.builder()
                .studio(studio)
                .filename(filename)
                .uploadFilename(randomFilename)
                .originalPath(filenameUtil.buildFilePath(randomFilename, extension))
                .resizedPath(filenameUtil.buildFilePath(randomFilename, RESIZED_EXTENSION))
                .build();
        studioImageRepository.save(studioImage);
    }

    private void saveReviewImage(Long reviewId, String filename, String randomFilename, String extension) {
        Review review = reviewService.findReviewById(reviewId);
        ReviewImage reviewImage = ReviewImage.builder()
                .review(review)
                .filename(filename)
                .uploadFilename(randomFilename)
                .originalPath(filenameUtil.buildFilePath(randomFilename, extension))
                .resizedPath(filenameUtil.buildFilePath(randomFilename, RESIZED_EXTENSION))
                .build();
        reviewImageRepository.save(reviewImage);
    }

    private void saveFacilityImage(Long studioId, String filename, String randomFilename, String extension) {
        Studio studio = studioService.findStudioById(studioId);
        FacilityImage facilityImage = FacilityImage.builder()
                .studio(studio)
                .filename(filename)
                .uploadFilename(randomFilename)
                .originalPath(filenameUtil.buildFilePath(randomFilename, extension))
                .resizedPath(filenameUtil.buildFilePath(randomFilename, RESIZED_EXTENSION))
                .build();
        facilityImageRepository.save(facilityImage);
    }

    /**
     * 생성된 랜덤 파일명 중복 체크
     * @return 중복되지 않는 랜덤 파일명
     */
    @Transactional(readOnly = true)
    public String generateRandomFilename(ImageType imageType) {
        String randomFilename;

        do {
            randomFilename = filenameUtil.generateRandomFileName();
        } while (isFilenameExists(randomFilename, imageType));

        return randomFilename;
    }

    private boolean isFilenameExists(String filename, ImageType imageType) {
        return switch (imageType) {
            case STUDIO -> studioImageRepository.findByUploadFilename(filename).isPresent();
            case REVIEW -> reviewImageRepository.findByUploadFilename(filename).isPresent();
            case FACILITY -> facilityImageRepository.findByUploadFilename(filename).isPresent();
        };
    }

    /**
     * 요청받은 이미지 업로드
     * @param request 요청 정보 (InputStream, Metadata)
     * @param filename 업로드 할 파일 이름
     */
    private void uploadImage(HttpServletRequest request, String filename) {
        try {
            ObjectMetadata metadata = createMetadata(request);
            s3ImageUtil.uploadImage(metadata, filename, request.getInputStream());
        } catch (IOException e) {
            throw new ToucheeseInternalServerErrorException(e.getMessage());
        }
    }

    /**
     * 요청으로부터 ObjectMetadata를 생성
     * @param request 요청 정보
     * @return 생성된 ObjectMetadata
     */
    private ObjectMetadata createMetadata(HttpServletRequest request) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(request.getContentType());
        metadata.setContentLength(request.getContentLength());
        return metadata;
    }

}
