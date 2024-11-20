package com.toucheese.conceptstudio.service;

import com.toucheese.concept.entity.Concept;
import com.toucheese.conceptstudio.entity.ConceptStudio;
import com.toucheese.conceptstudio.repository.ConceptStudioRepository;
import com.toucheese.studio.dto.StudioSearchResponse;
import com.toucheese.studio.entity.Studio;
import com.toucheese.studio.repository.StudioRepository;
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
public class ConceptStudioService {

    private final ConceptStudioRepository conceptStudioRepository;
    private final StudioRepository studioRepository;

    @Autowired
    private ConceptStudioService(ConceptStudioRepository conceptStudioRepository, StudioRepository studioRepository) {
        this.conceptStudioRepository = conceptStudioRepository;
        this.studioRepository = studioRepository;
    }

    /*
    public List<StudioSearchResponse> getStudiosByConceptId(Long conceptId) {
        // concept_id에 해당하는 ConceptStudio 데이터 조회
        List<ConceptStudio> conceptStudios = conceptStudioRepository.findByConceptId(conceptId);
        System.out.println("concept_id = " + conceptId);

        List<StudioSearchResponse> studioSearchResponses = new ArrayList<>();
        for (ConceptStudio conceptStudio : conceptStudios) {
            // ConceptStudio에서 studio 객체 가져오기
            Studio studio = conceptStudio.getStudio();

            // Studio 정보를 DTO로 변환
            if (studio != null) {
                studioSearchResponses.add(StudioSearchResponse.of(studio));
            }
        }

        System.out.println("studioSearchResponses=" + studioSearchResponses);
        return studioSearchResponses;
    }


     */
    public Page<StudioSearchResponse> getStudiosByConceptId(Long conceptId, Pageable pageable) {
        Page<ConceptStudio> conceptStudios = conceptStudioRepository.findByConceptId(conceptId,pageable);

        return conceptStudios.map(cs -> {
            Studio studio = cs.getStudio();
            if( studio == null) {
                throw new IllegalStateException("스튜디오 없음" + cs.getId());
            }
            return StudioSearchResponse.of(studio);
        });
    }



}
