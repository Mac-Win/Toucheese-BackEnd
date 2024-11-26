package com.toucheese.concept.controller;

import com.toucheese.concept.dto.ConceptDTO;
import com.toucheese.concept.entity.Concept;
import com.toucheese.concept.service.ConceptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/concepts")
public class ConceptController {

    private final ConceptService conceptService;

    @GetMapping
    public List<ConceptDTO> getAllConcepts() {
        return conceptService.getAllConcepts();
    }
}
