package com.toucheese.concept.service;

import com.toucheese.concept.entity.Concept;
import com.toucheese.concept.repository.ConceptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConceptService {

    private final ConceptRepository conceptRepository;

}
