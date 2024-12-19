package com.toucheese.image.entity;

import com.toucheese.question.entity.Question;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String uploadFilename;

    @Column(nullable = false)
    private String originalPath;

    @Column(nullable = false)
    private String resizedPath;

    @Builder
    public QuestionImage(Question question, String filename, String uploadFilename, String originalPath, String resizedPath) {
        this.question = question;
        this.filename = filename;
        this.uploadFilename = uploadFilename;
        this.originalPath = originalPath;
        this.resizedPath = resizedPath;
    }
}
