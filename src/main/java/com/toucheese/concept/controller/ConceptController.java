package com.toucheese.concept.controller;

import com.toucheese.concept.entity.Concept;
import com.toucheese.concept.service.ConceptService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/concept")
public class ConceptController {

    private final ConceptService conceptService;

    public ConceptController(ConceptService conceptService) {this.conceptService = conceptService;}

    @GetMapping
    public List<Concept> getAllConcepts() {
        return conceptService.getAllConcepts();
    }

    @GetMapping("/{id}")
    public Concept getConceptById(@PathVariable("id") Long id) {
        return conceptService.getConceptById(id);
    }
}
