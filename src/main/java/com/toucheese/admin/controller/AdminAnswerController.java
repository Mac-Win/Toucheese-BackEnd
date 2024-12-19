package com.toucheese.admin.controller;

import com.toucheese.admin.service.AdminAnswerService;
import com.toucheese.question.dto.AnswerResponse;
import com.toucheese.question.dto.QuestionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/admin/questions")
@RequiredArgsConstructor
@Tag(name = "관리자 문의하기 관리 API")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminAnswerController {
    private final AdminAnswerService adminAnswerService;
    // 문의글 전체 조회
    @Operation(summary = "문의글 전체 조회 (페이징 처리)", description = "관리자가 모든 문의글을 조회할 수 있습니다.")
    @GetMapping
    public ResponseEntity<Page<QuestionResponse>> getAllQuestions(Pageable pageable) {
        Page<QuestionResponse> questions = adminAnswerService.getAllQuestions(pageable);
        return ResponseEntity.ok(questions);
    }

    // 특정 문의글 조회
    @Operation(summary = "특정 문의글 조회", description = "관리자가 특정 문의글을 조회할 수 있습니다.")
    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponse> getQuestionById(@PathVariable Long id) {
        QuestionResponse question = adminAnswerService.getQuestionById(id);
        return ResponseEntity.ok(question);
    }

    // 답변 작성
    @Operation(summary = "답변 작성", description = "관리자가 답변을 작성합니다. 답변 작성 후 문의글 상태는 '답변완료'로 변경됩니다.")
    @PostMapping("/{id}/answers")
    public ResponseEntity<AnswerResponse> addAnswer(@PathVariable Long id, @RequestBody String content) {
        AnswerResponse answerResponse = adminAnswerService.addAnswer(id, content);
        return ResponseEntity.ok(answerResponse);
    }

    // 답변 수정
    @Operation(summary = "답변 수정", description = "관리자가 기존 답변을 수정합니다.")
    @PutMapping("/{questionId}/answers")
    public ResponseEntity<AnswerResponse> updateAnswer(@PathVariable Long questionId, @RequestBody String content) {
        AnswerResponse answerResponse = adminAnswerService.updateAnswer(questionId, content);
        return ResponseEntity.ok(answerResponse);
    }

    // 답변 삭제
    @Operation(summary = "답변 삭제", description = "관리자가 답변을 삭제합니다. 삭제 후 문의글 상태는 '답변대기'로 변경됩니다.")
    @DeleteMapping("/{questionId}/answers")
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long questionId) {
        adminAnswerService.deleteAnswer(questionId);
        return ResponseEntity.noContent().build();
    }
}
