package com.toucheese.image.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.toucheese.global.exception.ToucheeseInternalServerErrorException;
import com.toucheese.image.entity.StudioImage;
import com.toucheese.image.repository.StudioImageRepository;
import com.toucheese.image.util.FilenameUtil;
import com.toucheese.image.util.S3ImageUtil;
import com.toucheese.studio.entity.Studio;
import com.toucheese.studio.service.StudioService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final S3ImageUtil s3ImageUtil;
    private final FilenameUtil filenameUtil;
    private final StudioService studioService;
    private final StudioImageRepository studioImageRepository;

    private static final String RESIZED_EXTENSION = ".webp";

    /**
     * 기존 이미지를 업로드하기 위한 메서드
     * @param request 요청 정보 (InputStream, Metadata)
     * @param filename 파일 이름
     */
    public void uploadExistingImage(HttpServletRequest request, String filename) {
        uploadImage(request, filename);
    }

    /**
     * 새로운 이미지를 업로드하고 DB에 저장하는 메서드
     * @param request 요청 정보 (InputStream, Metadata)
     * @param filename 파일 이름
     * @param studioId 스튜디오 ID
     */
    @Transactional
    public void uploadNewImage(HttpServletRequest request, String filename, Long studioId) {
        Studio studio = studioService.findStudioById(studioId);

        String randomFilename = generateRandomFilename();
        String extension = filenameUtil.extractFileExtension(filename);
        uploadImage(request, randomFilename + extension);

        StudioImage studioImage = StudioImage.builder()
                .studio(studio)
                .filename(filename)
                .uploadFilename(randomFilename)
                .originalPath(filenameUtil.buildFilePath(randomFilename, extension))
                .resizedPath(filenameUtil.buildFilePath(randomFilename, RESIZED_EXTENSION))
                .build();

        studioImageRepository.save(studioImage);
    }

    /**
     * 생성된 랜덤 파일명 중복 체크
     * @return 중복되지 않는 랜덤 파일명
     */
    @Transactional(readOnly = true)
    public String generateRandomFilename() {
        String randomFilename = filenameUtil.generateRandomFileName();
        while (studioImageRepository.findByUploadFilename(randomFilename).isPresent()) {
            randomFilename = filenameUtil.generateRandomFileName();
        }
        return randomFilename;
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
