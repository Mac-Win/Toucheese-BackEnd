package com.toucheese.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.toucheese.reservation.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
