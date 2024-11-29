package com.toucheese.concept.service;

import com.toucheese.concept.dto.ConceptResponse;
import com.toucheese.concept.entity.Concept;
import com.toucheese.concept.repository.ConceptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConceptService {

    private final ConceptRepository conceptRepository;

    @Transactional(readOnly = true)
    public List<ConceptResponse> getAllConcepts() {
        List<Concept> concepts = conceptRepository.findAll();
        return concepts.stream()
                .map(ConceptResponse::of)
                .toList();
    }
}
