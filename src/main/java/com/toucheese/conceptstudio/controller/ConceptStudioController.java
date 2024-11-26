package com.toucheese.conceptstudio.controller;

import com.toucheese.conceptstudio.service.ConceptStudioService;
import com.toucheese.conceptstudio.dto.StudioResponse;
import com.toucheese.studio.entity.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/concepts")
public class ConceptStudioController {
    private final ConceptStudioService conceptStudioService;

    @GetMapping("/{conceptId}/studios")
    public Page<StudioResponse> getStudiosByConceptId(
            @PathVariable Long conceptId,
            @RequestParam int page
    ) {
        return conceptStudioService.getStudiosByConceptId(conceptId, page);
    }

    /**
     * 필터링 한 스튜디오 목록을 조회한다.
     * @param page 현재 페이지
     * @param conceptId 선택된 컨셉 요소 아이디
     * @param price 가격순 필터링 요소
     * @param rating 인기순 필터링 요소
     * @param locations 지역 필터링 요소
     * @return 현재 페이지에 해당하는 필터링 된 스튜디오 목록
     */
    @GetMapping("/{conceptId}/studios/filters")
    public Page<StudioResponse> getFilteredStudios(
            @PathVariable Long conceptId,
            @RequestParam int page,
            @RequestParam(required = false) Integer price,
            @RequestParam(required = false) Float rating,
            @RequestParam(required = false) List<Location> locations
    ) {
        return conceptStudioService.getFilteredStudiosOrderByName(
                page, conceptId, price, rating, locations
        );
    }}
