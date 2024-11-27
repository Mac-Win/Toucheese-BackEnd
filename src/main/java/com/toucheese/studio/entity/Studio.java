package com.toucheese.studio.entity;

import com.toucheese.conceptstudio.entity.ConceptStudio;
import com.toucheese.image.entity.StudioImage;
import com.toucheese.product.entity.Product;
import com.toucheese.review.entity.Review;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Location location;

    @Column(columnDefinition = "TEXT")
    private String notice;

    @OneToMany(mappedBy = "studio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<StudioImage> studioImages;

    @OneToMany(mappedBy = "studio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ConceptStudio> conceptStudios;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "studio_id")
    private List<Product> products;

    @OneToMany(mappedBy = "studio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Review> reviews;
}
