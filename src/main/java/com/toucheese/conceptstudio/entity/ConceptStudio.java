package com.toucheese.conceptstudio.entity;

import com.toucheese.concept.entity.Concept;
import com.toucheese.studio.entity.Studio;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "conceptstudio")
public class ConceptStudio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Concept.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "concept_id")
    private Concept concept;

    @ManyToOne(targetEntity = Studio.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "studio_id")
    private Studio studio;

}
