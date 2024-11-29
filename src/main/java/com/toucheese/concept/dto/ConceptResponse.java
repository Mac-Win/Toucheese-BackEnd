package com.toucheese.concept.dto;

import com.toucheese.concept.entity.Concept;
import lombok.Builder;

@Builder
public record ConceptResponse(Long id, String name) {

    public static ConceptResponse of(Concept concept) {
        return ConceptResponse.builder()
                .id(concept.getId())
                .name(concept.getName())
                .build();
    }
}
