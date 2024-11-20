package com.toucheese.conceptstudio.controller;

import com.toucheese.concept.entity.Concept;
import com.toucheese.conceptstudio.entity.ConceptStudio;
import com.toucheese.conceptstudio.service.ConceptStudioService;
import com.toucheese.studio.dto.StudioSearchResponse;
import com.toucheese.studio.entity.Studio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/concept")
public class ConceptStudioController {
    private final ConceptStudioService conceptStudioService;

    public ConceptStudioController(ConceptStudioService conceptStudioService) {
        this.conceptStudioService = conceptStudioService;
    }


    /*
    // concept_id에 해당하는 StudioSearchResponse를 반환
    @GetMapping("/{conceptId}/studios")
    public List<StudioSearchResponse> getStudiosByConceptId(@PathVariable Long conceptId) {
        return conceptStudioService.getStudiosByConceptId(conceptId);
    }
     */

    @GetMapping("/{conceptId}/studios")
    public Page<StudioSearchResponse> getStudiosByConceptId(
            @PathVariable Long conceptId,
            Pageable pageable
    ) {
        return conceptStudioService.getStudiosByConceptId(conceptId,pageable);
    }
}