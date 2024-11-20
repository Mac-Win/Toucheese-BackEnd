package com.toucheese.concept.repository;

import com.toucheese.concept.entity.Concept;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConceptRepository extends JpaRepository<Concept, Long> {

}
