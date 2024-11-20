package com.toucheese.concept.service;

import com.toucheese.concept.entity.Concept;
import com.toucheese.concept.repository.ConceptRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConceptService {

    private final ConceptRepository conceptRepository;

    public ConceptService(ConceptRepository conceptRepository) {this.conceptRepository = conceptRepository;}

    // 전체 Concept 불러오기
    public List<Concept> getAllConcepts() {
        return conceptRepository.findAll();
    }
    //{id} 별 Concept 불러오기
    public Concept getConceptById(Long id) {
        return conceptRepository.findById(id).orElse(null);
    }
}
