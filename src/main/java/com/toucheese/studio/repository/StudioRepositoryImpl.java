package com.toucheese.studio.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
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

    private static final QStudio QSTUDIO = QStudio.studio;
    private static final Integer PRICE_CONDITION = 200_000;

    /**
     * 필터링 후 이름으로 정렬된 스튜디오 목록을 반환한다.
     * 각 요소가 null인 경우는 필터링이 제외된 경우로 함.
     *
     * @param price 가격 필터링을 위한 요소
     * @param rating 인기 필터링을 위한 요소
     * @param locations 지역 필터링을 위한 요소
     * @param pageable 페이지 객체
     * @return 현재 페이지에 해당되는 필터링 후 정렬된 스튜디오 목록
     */
    @Override
    public Page<Studio> getFilteredStudiosOrderByName(Integer price, Float rating, List<Location> locations, Pageable pageable) {
        BooleanBuilder booleanBuilder = checkFilteringComponent(price, rating, locations);

        JPAQuery<Studio> query = jpaQueryFactory.selectFrom(QSTUDIO)
                .where(booleanBuilder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(QSTUDIO.name.asc());

        List<Studio> studios = query.fetch();

        return new PageImpl<>(studios, pageable, studios.size());
    }


    /**
     * 필터링 조건을 확인하고 동적으로 조건을 추가하기 위한 메서드
     * @param price 가격, 해당 값 이하(less than or equal to; loe) 데이터 필터링 / 20만 이상일 때는 이상 필터링
     * @param rating 별점, 해당 값 이상(greater than or equal to; goe) 데이터 필터링
     * @param locations 지역, 해당 값에 해당하는 데이터 필터링
     * @return 각 필터링 요소를 확인하여 생성된 조건
     */
    private BooleanBuilder checkFilteringComponent(Integer price, Float rating, List<Location> locations) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 지역 필터링 요소 확인
        if (locations != null && !locations.isEmpty()) {
            booleanBuilder.and(QSTUDIO.location.in(locations));
        }

        // 인기 필터링 요소 확인
        if (rating != null) {
            booleanBuilder.and(QSTUDIO.rating.goe(rating));
        }

        // 가격 필터링 요소 확인
        if (price != null) {
            if (price < PRICE_CONDITION) {
                booleanBuilder.and(QSTUDIO.price.loe(price));
            } else {
                booleanBuilder.and(QSTUDIO.price.goe(price));
            }
        }

        return booleanBuilder;
    }

}
