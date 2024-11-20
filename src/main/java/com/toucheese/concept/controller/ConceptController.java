package com.toucheese.concept.controller;

import com.toucheese.concept.entity.Concept;
import com.toucheese.concept.service.ConceptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/concepts")
public class ConceptController {

    private final ConceptService conceptService;

    public ConceptController(ConceptService conceptService) {this.conceptService = conceptService;}

}
