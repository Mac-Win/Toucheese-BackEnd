package com.toucheese.conceptstudio.repository;

import com.toucheese.conceptstudio.entity.ConceptStudio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConceptStudioRepository extends JpaRepository<ConceptStudio, Long> {
    Page<ConceptStudio> findByConceptId(Long conceptId, Pageable pageable);
}
