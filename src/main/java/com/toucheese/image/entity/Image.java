package com.toucheese.image.entity;

import com.toucheese.studio.entity.Studio;
import jakarta.persistence.*;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studio_id")
    private Studio studio;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String url;
}
