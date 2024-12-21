package com.toucheese.reservation.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.toucheese.reservation.entity.Reservation;
import com.toucheese.reservation.entity.ReservationStatus;
import com.toucheese.reservation.repository.ReservationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class ReservationSchedulerService {
	private final ReservationRepository reservationRepository;

	@Scheduled(cron = "0 1 * * * *")
	@Transactional
	public void markReservationsAsCompleted() {
		LocalDateTime currentTime = LocalDateTime.now();

		List<Reservation> reservations = reservationRepository.findAllByStatus(ReservationStatus.예약확정);

		for (Reservation reservation : reservations) {
			LocalDateTime reservationDateTime = LocalDateTime.of(
				reservation.getCreateDate(), reservation.getCreateTime()
			);

			if (reservationDateTime.isBefore(currentTime)) {
				reservation.updateStatus(ReservationStatus.촬영완료);
				log.info("예약 ID {} 상태가 '촬영완료'로 변경되었습니다.", reservation.getId());
			}
		}
	}
}