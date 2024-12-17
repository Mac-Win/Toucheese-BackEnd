package com.toucheese.question.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.toucheese.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AnswerStatus answerStatus;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
