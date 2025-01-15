package com.toucheese.conceptstudio.repository;

import com.toucheese.conceptstudio.entity.ConceptStudio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConceptStudioRepository extends JpaRepository<ConceptStudio, Long> {

    @Query("SELECT cs FROM ConceptStudio cs JOIN FETCH cs.studio s LEFT JOIN FETCH s.studioImages WHERE cs.concept.id = :conceptId")
    Page<ConceptStudio> findByConceptId(Long conceptId, Pageable pageable);
}
