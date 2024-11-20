package com.toucheese.conceptstudio.controller;

import com.toucheese.concept.entity.Concept;
import com.toucheese.conceptstudio.entity.ConceptStudio;
import com.toucheese.conceptstudio.service.ConceptStudioService;
import com.toucheese.studio.dto.StudioSearchResponse;
import com.toucheese.studio.entity.Studio;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/concepts")
public class ConceptStudioController {
    private final ConceptStudioService conceptStudioService;

    @GetMapping("/{conceptId}/studios")
    public Page<StudioSearchResponse> getStudiosByConceptId(
            @PathVariable Long conceptId,
            Pageable pageable
    ) {
        return conceptStudioService.getStudiosByConceptId(conceptId,pageable);
    }
}
