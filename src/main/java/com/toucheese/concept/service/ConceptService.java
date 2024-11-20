package com.toucheese.concept.service;

import com.toucheese.concept.entity.Concept;
import com.toucheese.concept.repository.ConceptRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConceptService {

    private final ConceptRepository conceptRepository;

    public ConceptService(ConceptRepository conceptRepository) {this.conceptRepository = conceptRepository;}

}
