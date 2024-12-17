package com.toucheese.reservation.entity;

import lombok.Getter;

@Getter
public enum ReservationStatus {
	예약접수,
	예약확정,
	예약취소,
	촬영완료
}