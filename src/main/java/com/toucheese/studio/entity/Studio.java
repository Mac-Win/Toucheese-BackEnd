package com.toucheese.studio.entity;

import com.toucheese.image.entity.Image;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Studio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String profileImage;

    private Integer price;

    private Float rating;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String location;

    @OneToMany(mappedBy = "studio", fetch = FetchType.LAZY)
    private List<Image> images;
}
