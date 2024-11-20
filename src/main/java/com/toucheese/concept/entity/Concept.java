package com.toucheese.concept.entity;

import com.toucheese.conceptstudio.entity.ConceptStudio;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Concept {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "concept", fetch = FetchType.LAZY)
    private List<ConceptStudio> conceptStudios;
}
