package com.toucheese.image.service;

import static com.toucheese.image.util.FilenameUtil.extractFileExtension;
import static com.toucheese.image.util.MetadataUtil.createMetadata;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.toucheese.global.exception.ToucheeseBadRequestException;
import com.toucheese.global.exception.ToucheeseInternalServerErrorException;
import com.toucheese.image.entity.ImageInfo;
import com.toucheese.image.entity.ImageType;
import com.toucheese.image.util.S3ImageUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final S3ImageUtil s3ImageUtil;

    private final ImageFacade imageFacade;
    private final ImageInfoService imageInfoService;

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
                case STUDIO -> imageFacade.saveStudioImage(entityId, imageInfo, extension);
                case REVIEW -> imageFacade.saveReviewImage(entityId, imageInfo, extension);
                case FACILITY -> imageFacade.saveFacilityImage(entityId, imageInfo, extension);
                case QUESTION -> imageFacade.saveQuestionImage(entityId, imageInfo, extension);
                default -> throw new ToucheeseBadRequestException(imageType + ": 존재하지 않는 형식 입니다.");
            }
        }
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
