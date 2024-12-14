package com.toucheese.image.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class FilenameUtil {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int FILE_NAME_LENGTH = 7;
    private static final SecureRandom random = new SecureRandom();

    /**
     * 랜덤 파일명 생성 메서드
     * @return 생성된 랜덤 파일명
     */
    public String generateRandomFileName() {
        StringBuilder sb = new StringBuilder(FILE_NAME_LENGTH);
        for (int i = 0; i < FILE_NAME_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }

    /**
     * 파일 경로 생성
     * @param filename 파일 이름
     * @param extension 파일 확장자
     * @return 파일 경로
     */
    public String buildFilePath(String filename, String extension) {
        return "/" + filename + extension;
    }

    /**
     * 파일 확장자 추출
     * @param filename 파일 이름
     * @return 확장자
     */
    public String extractFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf('.'));
    }
}
