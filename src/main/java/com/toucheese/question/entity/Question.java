package com.toucheese.question.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.toucheese.image.entity.QuestionImage;
import com.toucheese.member.entity.Member;
import jakarta.persistence.*;
import java.util.List;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AnswerStatus answerStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id")
    private Answer answer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "question")
    private List<QuestionImage> questionImages;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 답변 상태와 답변 연결
    public void completeAnswer(Answer answer) {
        this.answer = answer;
        this.answerStatus = AnswerStatus.답변완료;
    }

    // 답변 상태를 답변대기로 변경
    public void resetAnswer() {
        this.answer = null;
        this.answerStatus = AnswerStatus.답변대기;
    }
}
