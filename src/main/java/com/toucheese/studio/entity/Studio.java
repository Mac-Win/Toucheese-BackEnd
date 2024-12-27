package com.toucheese.studio.entity;

import java.util.List;

import com.toucheese.conceptstudio.entity.ConceptStudio;
import com.toucheese.image.entity.FacilityImage;
import com.toucheese.image.entity.StudioImage;
import com.toucheese.product.entity.Product;
import com.toucheese.review.entity.Review;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Studio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String profileImage;

    private Integer price;

    @Column(nullable = false)
    private String address;

    private Float rating;

    @OneToMany(mappedBy = "studio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OperatingHour> operatingHours;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Location location;

    @Column(columnDefinition = "TEXT")
    private String notice;

    @OneToMany(mappedBy = "studio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<StudioImage> studioImages;

    @OneToMany(mappedBy = "studio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FacilityImage> FacilityImages;

    @OneToMany(mappedBy = "studio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ConceptStudio> conceptStudios;

    @OneToMany(mappedBy = "studio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Product> products;

    @OneToMany(mappedBy = "studio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Review> reviews;
}
