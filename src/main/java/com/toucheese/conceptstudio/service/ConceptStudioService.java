package com.toucheese.conceptstudio.service;

import com.toucheese.concept.entity.Concept;
import com.toucheese.conceptstudio.dto.ConceptStudioResponse;
import com.toucheese.conceptstudio.entity.ConceptStudio;
import com.toucheese.conceptstudio.repository.ConceptStudioRepository;
import com.toucheese.studio.dto.StudioSearchResponse;
import com.toucheese.studio.entity.Studio;
import com.toucheese.studio.repository.StudioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConceptStudioService {

    private final ConceptStudioRepository conceptStudioRepository;
    private final StudioRepository studioRepository;

    public Page<ConceptStudioResponse> getStudiosByConceptId(Long conceptId, Pageable pageable) {
        Page<ConceptStudio> conceptStudios = conceptStudioRepository.findByConceptId(conceptId, pageable);

        return conceptStudios.map(cs -> {
            Studio studio = cs.getStudio();

            List<String> imageUrls = studio.getImages().stream()
                .map(image -> image.getUrl())
                .collect(Collectors.toList());

            return ConceptStudioResponse.of(studio, imageUrls);
        });
    }
}