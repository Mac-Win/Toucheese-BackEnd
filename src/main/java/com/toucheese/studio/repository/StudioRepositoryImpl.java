package com.toucheese.studio.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toucheese.concept.entity.QConcept;
import com.toucheese.conceptstudio.entity.QConceptStudio;
import com.toucheese.studio.entity.Location;
import com.toucheese.studio.entity.QStudio;
import com.toucheese.studio.entity.Studio;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StudioRepositoryImpl implements StudioCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    private static final QStudio Q_STUDIO = QStudio.studio;
    private static final QConcept Q_CONCEPT = QConcept.concept;
    private static final QConceptStudio Q_CONCEPT_STUDIO = QConceptStudio.conceptStudio;
    private static final Integer PRICE_CONDITION = 200_000;

    /**
     * 필터링 후 이름으로 정렬된 스튜디오 목록을 반환한다.
     * 각 요소가 null인 경우는 필터링이 제외된 경우로 함.
     * (+ 임시로 concept 아이디가 없을 경우 전체 리스트에서 조회할 수 있도록 함)
     *
     * @param price 가격 필터링을 위한 요소
     * @param rating 인기 필터링을 위한 요소
     * @param locations 지역 필터링을 위한 요소
     * @param pageable 페이지 객체
     * @param conceptId 선택된 컨셉 요소 아이디
     * @return 현재 페이지에 해당되는 필터링 후 정렬된 스튜디오 목록
     */
    @Override
    public Page<Studio> getFilteredStudiosOrderByName(Integer price, Float rating, List<Location> locations, Long conceptId, Pageable pageable) {
        BooleanBuilder booleanBuilder = checkFilteringComponent(conceptId, price, rating, locations);

        JPAQuery<Studio> query = jpaQueryFactory.selectFrom(Q_STUDIO)
                .leftJoin(Q_STUDIO.conceptStudios, Q_CONCEPT_STUDIO)
                .leftJoin(Q_CONCEPT_STUDIO.concept, Q_CONCEPT)
                .where(booleanBuilder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(Q_STUDIO.name.asc());

        List<Studio> studios = query.fetch();

        return new PageImpl<>(studios, pageable, studios.size());
    }


    /**
     * 필터링 조건을 확인하고 동적으로 조건을 추가하기 위한 메서드
     * @param conceptId 컨셉, 해당 컨셉 아이디에 해당하는 데이터 필터링
     * @param price 가격, 해당 값 이하(less than or equal to; loe) 데이터 필터링 / 20만 이상일 때는 이상 필터링
     * @param rating 별점, 해당 값 이상(greater than or equal to; goe) 데이터 필터링
     * @param locations 지역, 해당 값에 해당하는 데이터 필터링
     * @return 각 필터링 요소를 확인하여 생성된 조건
     */
    private BooleanBuilder checkFilteringComponent(Long conceptId, Integer price, Float rating, List<Location> locations) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        checkTargetConceptId(conceptId, booleanBuilder);
        checkTargetLocations(locations, booleanBuilder);
        checkTargetPrice(price, booleanBuilder);
        checkTargetRating(rating, booleanBuilder);

        return booleanBuilder;
    }

    /**
     * 컨셉별 요소 확인 메서드
     * @param conceptId 선택된 컨셉 요소 아이디
     * @param booleanBuilder 조건 설정 빌더
     */
    private void checkTargetConceptId (Long conceptId, BooleanBuilder booleanBuilder) {
        if (conceptId != null) {
            booleanBuilder.and(Q_CONCEPT_STUDIO.concept.id.eq(conceptId));
        }
    }

    /**
     * 지역별 필터링 요소 확인 메서드
     * @param targetLocations 선택된 지역 리스트 조건
     * @param booleanBuilder 조건 설정 빌더
     */
    private void checkTargetLocations(List<Location> targetLocations, BooleanBuilder booleanBuilder) {
        if (targetLocations != null && !targetLocations.isEmpty()) {
            booleanBuilder.and(Q_STUDIO.location.in(targetLocations));
        }
    }

    /**
     * 별점 필터링 요소 확인 메서드
     * @param targetRating 선택된 별점 조건
     * @param booleanBuilder 조건 설정 빌더
     */
    private void checkTargetRating(Float targetRating, BooleanBuilder booleanBuilder) {
        if (targetRating != null) {
            float epsilon = 0.0001f; // 허용 오차 설정
            booleanBuilder.and(Q_STUDIO.rating.goe(targetRating - epsilon));
        }
    }

    /**
     * 가격 필터링 요소 확인 메서드
     * @param targetPrice 선택된 가격 조건
     * @param booleanBuilder 조건 설정 빌더
     */
    private void checkTargetPrice(Integer targetPrice, BooleanBuilder booleanBuilder) {
        if (targetPrice != null) {
            if (targetPrice < PRICE_CONDITION) {
                booleanBuilder.and(Q_STUDIO.price.loe(targetPrice));
            } else {
                booleanBuilder.and(Q_STUDIO.price.goe(targetPrice));
            }
        }
    }

}
