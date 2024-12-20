package com.toucheese.image.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String filename;

	@Column(nullable = false)
	private String uploadFilename;

	@Builder
	public ImageInfo(String filename, String uploadFilename) {
		this.filename = filename;
		this.uploadFilename = uploadFilename;
	}
}
