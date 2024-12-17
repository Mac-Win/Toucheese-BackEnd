package com.toucheese.reservation.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.toucheese.reservation.entity.Reservation;
import com.toucheese.reservation.entity.ReservationStatus;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	@Query("SELECT r FROM Reservation r WHERE " +
		"(:status IS NULL OR r.status = :status) AND " +
		"(:createDate IS NULL OR r.createDate = :createDate)")
	Page<Reservation> findReservations(
		@Param("status") ReservationStatus status,
		@Param("createDate") LocalDate createDate,
		Pageable pageable
	);

	Page<Reservation> findPagedReservationsByMemberId(Long memberId, Pageable pageable);

	Optional<Reservation> findByIdAndMemberId(Long reservationId, Long memberId);

	List<Reservation> findAllByStatus(ReservationStatus reservationStatus);
}