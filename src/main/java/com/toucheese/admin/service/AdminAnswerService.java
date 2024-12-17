package com.toucheese.admin.service;

import com.toucheese.question.dto.AnswerResponse;
import com.toucheese.question.dto.QuestionResponse;
import com.toucheese.question.entity.Answer;
import com.toucheese.question.entity.AnswerStatus;
import com.toucheese.question.entity.Question;
import com.toucheese.question.repository.AnswerRepository;
import com.toucheese.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminAnswerService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;


    public Page<QuestionResponse> getAllQuestions(Pageable pageable) {
        Page<Question> questions = questionRepository.findAll(pageable);
        return questions.map(QuestionResponse::of);
    }

    public QuestionResponse getQuestionById(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 x"));
        return QuestionResponse.of(question);
    }

    @Transactional
    public AnswerResponse addAnswer(Long questionId, String content) {

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        Answer answer = Answer.builder()
                .question(question)
                .content(content)
                .createDate(LocalDate.now())
                .build();

        Answer savedAnswer = answerRepository.save(answer);
        question.updateAnswer(savedAnswer);
        questionRepository.save(question);

        return AnswerResponse.of(savedAnswer);
    }

    // 답변 수정
    @Transactional
    public AnswerResponse updateAnswer(Long questionId, String content) {
        Answer answer = answerRepository.findByQuestionId(questionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 답변이 존재하지 않습니다."));

        answer.updateAnswer(content);
        return AnswerResponse.of(answer);
    }

    // 답변 삭제
    @Transactional
    public void deleteAnswer(Long questionId) {
        Answer answer = answerRepository.findByQuestionId(questionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 답변이 존재하지 않습니다."));

        Question question = questionRepository.findById(questionId)
                        .orElseThrow(()-> new IllegalArgumentException(("해당 게시글 존재하지 않습니다.")));
        question.resetAnswer();
        questionRepository.save(question);  // 질문 상태 업데이트

        answerRepository.delete(answer);  // 답변 삭제
    }
}
