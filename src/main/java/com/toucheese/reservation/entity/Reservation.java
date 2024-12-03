package com.toucheese.reservation.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.toucheese.product.entity.Product;
import com.toucheese.studio.entity.Studio;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer totalPrice;

	private String name;

	private String phone;

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private LocalDate createDate;

	@JsonFormat(pattern = "HH:mm", timezone = "Asia/Seoul")
	private LocalTime createTime;

	private Integer personnel;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "studio_id")
	private Studio studio;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "reservation_id")
	private List<ReservationProductAddOption> reservationProductAddOptions;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ReservationStatus status;

	@PrePersist
	private void prePersist() {
		if (this.status == null) {
			this.status = ReservationStatus.예약대기;
		}
	}

	public Reservation(Product product, Studio studio, Integer totalPrice, String name, String phone,
		LocalDate createDate, LocalTime createTime, Integer personnel,
		List<ReservationProductAddOption> reservationProductAddOptions) {
		this.product = product;
		this.studio = studio;
		this.totalPrice = totalPrice;
		this.name = name;
		this.phone = phone;
		this.createDate = createDate;
		this.createTime = createTime.withSecond(0).withNano(0); // 초와 나노초 제거
		this.personnel = personnel;
		this.reservationProductAddOptions = reservationProductAddOptions;
	}
}