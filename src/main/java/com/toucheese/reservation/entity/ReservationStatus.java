package com.toucheese.reservation.entity;

import lombok.Getter;

@Getter
public enum ReservationStatus {
	예약대기,
	예약완료,
	예약취소,
	결제대기,
	결제완료
}
