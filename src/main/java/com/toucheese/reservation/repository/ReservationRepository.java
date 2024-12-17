package com.toucheese.reservation.repository;

import java.time.LocalDate;

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
}