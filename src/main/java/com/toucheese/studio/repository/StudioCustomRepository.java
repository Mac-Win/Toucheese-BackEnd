package com.toucheese.studio.repository;

import com.toucheese.studio.entity.Location;
import com.toucheese.studio.entity.Studio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudioCustomRepository {


    /**
     * 필터링 후 이름으로 정렬된 스튜디오 목록을 반환한다.
     * 각 요소가 null인 경우는 필터링이 제외된 경우로 함.
     *
     * @param price 가격순 필터링을 위한 요소
     * @param rating 인기순 필터링을 위한 요소
     * @param locations 지역 필터링을 위한 요소
     * @param conceptId 선택된 컨셉 요소 아이디
     * @param pageable 페이지 객체
     * @return 각 요소에 따른 필터링 된 스튜디오 목록을 페이지로 반환한다.
     */
    Page<Studio> getFilteredStudiosOrderByName(Integer price, Float rating, List<Location> locations, Long conceptId, Pageable pageable);

}
