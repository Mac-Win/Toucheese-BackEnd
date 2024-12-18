package com.toucheese.image.entity;

import com.toucheese.studio.entity.Studio;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FacilityImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "studio_id")
	private Studio studio;

	@Column(nullable = false)
	private String filename;

	@Column(nullable = false)
	private String uploadFilename;

	@Column(nullable = false)
	private String originalPath;

	@Column(nullable = false)
	private String resizedPath;

	@Builder
	public FacilityImage(Studio studio, String filename, String uploadFilename, String originalPath, String resizedPath) {
		this.studio = studio;
		this.filename = filename;
		this.uploadFilename = uploadFilename;
		this.originalPath = originalPath;
		this.resizedPath = resizedPath;
	}
}
