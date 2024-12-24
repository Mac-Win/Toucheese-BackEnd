package com.toucheese.image.service;

import com.toucheese.image.entity.*;
import com.toucheese.image.repository.FacilityImageRepository;
import com.toucheese.image.repository.QuestionImageRepository;
import com.toucheese.image.repository.ReviewImageRepository;
import com.toucheese.image.repository.StudioImageRepository;
import com.toucheese.question.entity.Question;
import com.toucheese.question.service.QuestionReadService;
import com.toucheese.review.entity.Review;
import com.toucheese.review.service.ReviewQueryService;
import com.toucheese.studio.entity.Studio;
import com.toucheese.studio.service.StudioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.toucheese.image.util.FilenameUtil.buildFilePath;

@Component
@RequiredArgsConstructor
public class ImageFacade {

    private final StudioService studioService;
    private final ReviewQueryService reviewQueryService;
    private final QuestionReadService questionReadService;

    private final StudioImageRepository studioImageRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final FacilityImageRepository facilityImageRepository;
    private final QuestionImageRepository questionImageRepository;

    private static final String RESIZED_EXTENSION = ".webp";

    /**
     * 스튜디오 이미지 저장
     * @param studioId 스튜디오 ID
     * @param imageInfo 이미지 정보
     * @param extension 기존 이미지에서 추출한 확장자
     */
    @Transactional
    public void saveStudioImage(Long studioId, ImageInfo imageInfo, String extension) {
        Studio studio = studioService.findStudioById(studioId);
        StudioImage studioImage = StudioImage.builder()
                .studio(studio)
                .originalPath(buildFilePath(imageInfo.getUploadFilename(), extension))
                .resizedPath(buildFilePath(imageInfo.getUploadFilename(), RESIZED_EXTENSION))
                .imageInfo(imageInfo)
                .build();
        studioImageRepository.save(studioImage);
    }

    /**
     * 리뷰 이미지 저장
     * @param reviewId 리뷰 ID
     * @param imageInfo 이미지 정보
     * @param extension 기존 이미지에서 추출한 확장자
     */
    @Transactional
    public void saveReviewImage(Long reviewId, ImageInfo imageInfo, String extension) {
        Review review = reviewQueryService.findReviewById(reviewId);
        ReviewImage reviewImage = ReviewImage.builder()
                .review(review)
                .originalPath(buildFilePath(imageInfo.getUploadFilename(), extension))
                .resizedPath(buildFilePath(imageInfo.getUploadFilename(), RESIZED_EXTENSION))
                .imageInfo(imageInfo)
                .build();
        reviewImageRepository.save(reviewImage);
    }

    /**
     * 시설 이미지 저장
     * @param studioId 스튜디오 ID
     * @param imageInfo 이미지 정보
     * @param extension 기존 이미지에서 추출한 확장자
     */
    @Transactional
    public void saveFacilityImage(Long studioId, ImageInfo imageInfo, String extension) {
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
     * 문의 이미지 저장
     * @param questionId 문의 ID
     * @param imageInfo 이미지 정보
     * @param extension 기존 이미지에서 추출한 확장자
     */
    @Transactional
    public void saveQuestionImage(Long questionId, ImageInfo imageInfo, String extension) {
        Question question = questionReadService.findQuestionById(questionId);
        QuestionImage questionImage = QuestionImage.builder()
                .question(question)
                .originalPath(buildFilePath(imageInfo.getUploadFilename(), extension))
                .resizedPath(buildFilePath(imageInfo.getUploadFilename(), RESIZED_EXTENSION))
                .imageInfo(imageInfo)
                .build();
        questionImageRepository.save(questionImage);
    }

}
