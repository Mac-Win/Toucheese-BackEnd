package com.toucheese.question.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity @Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    private String content;

    private LocalDate createDate;

    public void updateAnswer(String content) {
        this.content = content;
        this.createDate = LocalDate.now();
    }
}
