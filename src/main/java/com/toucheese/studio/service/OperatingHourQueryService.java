package com.toucheese.studio.service;

import com.toucheese.studio.dto.CalendarDayResponse;
import com.toucheese.studio.entity.OperatingHour;
import com.toucheese.studio.repository.OperatingHourRepository;
import com.toucheese.studio.util.DateUtils;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OperatingHourQueryService {

    private final OperatingHourRepository operatingHourRepository;

    /**
     * 특정 월의 휴무일과 영업시간을 반환
     *
     * @param studioId 스튜디오 ID
     * @param yearMonth 2024-12 연도월
     * @return 날짜별 상태 리스트
     */
    @Transactional(readOnly = true)
    public List<CalendarDayResponse> getMonthlyCalendar(Long studioId, String yearMonth) {
        YearMonth targetYearMonth = (yearMonth != null && !yearMonth.isEmpty())
                ? YearMonth.parse(yearMonth)
                : YearMonth.now(ZoneId.of("Asia/Seoul"));

        LocalDate firstDay = targetYearMonth.atDay(1);
        LocalDate lastDay = firstDay.with(TemporalAdjusters.lastDayOfMonth());

        // 스튜디오의 요일별 영업시간 한 번에 조회
        Map<String, OperatingHour> operatingHours = operatingHourRepository.findByStudioId(studioId)
                .stream()
                .collect(Collectors.toMap(OperatingHour::getDayOfWeek, oh -> oh));

        // 날짜별로 CalendarDayResponse 생성
        List<CalendarDayResponse> calendarDays = new ArrayList<>();
        for (LocalDate date = firstDay; !date.isAfter(lastDay); date = date.plusDays(1)) {
            String dayOfWeek = DateUtils.getDayOfWeekFromDate(date.toString());
            OperatingHour operatingHour = operatingHours.get(dayOfWeek);

            calendarDays.add(CalendarDayResponse.of(date, operatingHour));
        }
        return calendarDays;
    }
}
