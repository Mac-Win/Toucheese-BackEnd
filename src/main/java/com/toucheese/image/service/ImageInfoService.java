package com.toucheese.image.service;

import com.toucheese.image.entity.ImageInfo;
import com.toucheese.image.repository.ImageInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.toucheese.image.util.FilenameUtil.generateRandomFileName;

@Service
@RequiredArgsConstructor
public class ImageInfoService {

    private final ImageInfoRepository imageInfoRepository;

    /**
     * 현재 이미지 파일 이름과 랜덤 생성된 파일 이름으로 이미지 정보 생성
     * @param filename 현재 이미지 파일 이름
     * @return 생성된 이미지 정보
     */
    @Transactional
    public ImageInfo createImageInfo(String filename) {
        return imageInfoRepository.save(
                ImageInfo.builder()
                        .filename(filename)
                        .uploadFilename(generateRandomFilename())
                        .build()
        );
    }

    /**
     * 파일 이름을 랜덤으로 생성하기 위한
     * @return 생성된 파일 이름
     */
    private String generateRandomFilename() {
        String randomFilename;

        do {
            randomFilename = generateRandomFileName();
        } while (isFilenameExists(randomFilename));

        return randomFilename;
    }

    /**
     * 랜덤 생성된 파일 이름이 존재하는지 검증
     * @param generatedFilename 생성된 파일 이름
     * @return true / false
     */
    private boolean isFilenameExists(String generatedFilename) {
        return imageInfoRepository.findByUploadFilename(generatedFilename).isPresent();
    }
}
