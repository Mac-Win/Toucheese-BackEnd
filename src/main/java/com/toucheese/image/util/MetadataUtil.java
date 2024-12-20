package com.toucheese.image.util;

import com.amazonaws.services.s3.model.ObjectMetadata;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

public class MetadataUtil {

    /**
     * 요청으로부터 ObjectMetadata를 생성
     * @param request 요청 정보
     * @return 생성된 ObjectMetadata
     */
    public static ObjectMetadata createMetadata(HttpServletRequest request) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(request.getContentType());
        metadata.setContentLength(request.getContentLength());
        return metadata;
    }

    /**
     * 요청 파일로부터 ObjectMetadata를 생성
     * @param uploadFile 파일 정보
     * @return 생성된 ObjectMetadata
     */
    public static ObjectMetadata createMetadata(MultipartFile uploadFile) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(uploadFile.getContentType());
        metadata.setContentLength(uploadFile.getSize());
        return metadata;
    }

}
