package com.toucheese.concept.dto;

import com.toucheese.concept.entity.Concept;
import lombok.Builder;

@Builder
public record ConceptDTO(Long id, String name) {

    public static ConceptDTO of(Concept concept) {
        return ConceptDTO.builder()
                .id(concept.getId())
                .name(concept.getName())
                .build();
    }
}
