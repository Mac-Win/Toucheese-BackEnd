package com.toucheese.studio.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OperatingHour {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String dayOfWeek;

	@Column(nullable = false)
	private String openTime;

	@Column(nullable = false)
	private String closeTime;

	private Integer term;

	private LocalDate create_date;

	private LocalDate update_date;
}
