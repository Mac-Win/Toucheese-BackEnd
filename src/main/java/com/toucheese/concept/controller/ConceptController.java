package com.toucheese.concept.controller;

import com.toucheese.concept.dto.ConceptResponse;
import com.toucheese.concept.service.ConceptService;

import com.toucheese.global.data.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/concepts")
@Tag(name = "컨셉 조회 API")
public class ConceptController {

    private final ConceptService conceptService;

    @GetMapping
    @Operation(summary = "컨셉 조회", description =
        "컨셉 6가지에 대한 조회 : "
            + "1. 생동감 있는 실물 느낌, "
            + "2. 플래쉬 / 아이돌 느낌, "
            + "3. 흑백 / 블루 배우 느낌, "
            + "4. 내추럴 화보 느낌, "
            + "5. 선명하고 인형같은 느낌, "
            + "6. 필터 / 수채화 그림체 느낌")
    public ResponseEntity<?> getAllConcepts() {
        return ApiResponse.getObjectSuccess(conceptService.getAllConcepts());
    }
}
