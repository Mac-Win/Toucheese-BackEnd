package com.toucheese.reservation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.toucheese.reservation.entity.Reservation;
import com.toucheese.reservation.entity.ReservationStatus;

public interface ReservationCommandRepository extends JpaRepository<Reservation, Long> {

	List<Reservation> findAllByStatus(ReservationStatus reservationStatus);
}