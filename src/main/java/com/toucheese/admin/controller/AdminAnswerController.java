package com.toucheese.admin.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.toucheese.admin.service.AdminAnswerService;
import com.toucheese.global.data.ApiResponse;
import com.toucheese.question.dto.AnswerRequest;
import com.toucheese.question.dto.QuestionDetailResponse;
import com.toucheese.question.dto.QuestionResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

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
    public ResponseEntity<Page<QuestionResponse>> getAllQuestions(@RequestParam int page) {
        Page<QuestionResponse> questions = adminAnswerService.getAllQuestions(page);
        return ApiResponse.getObjectSuccess(questions);
    }

    // 특정 문의글 조회
    @Operation(summary = "특정 문의글 조회", description = "관리자가 특정 문의글을 조회할 수 있습니다.")
    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionDetailResponse> getQuestionById(@PathVariable Long questionId) {
        QuestionDetailResponse question = adminAnswerService.findQuestionDetail(questionId);
        return ApiResponse.getObjectSuccess(question);
    }

    // 답변 작성
    @Operation(summary = "답변 작성", description = "관리자가 답변을 작성합니다. 답변 작성 후 문의글 상태는 '답변완료'로 변경됩니다.")
    @PostMapping("/{questionId}/answers")
    public ResponseEntity<?> addAnswer(@PathVariable Long questionId, @RequestBody AnswerRequest request) {
        adminAnswerService.addAnswer(questionId, request.title(), request.content());
        return ApiResponse.createdSuccess("답변이 성공적으로 생성되었습니다.");
    }

    // 답변 수정
    @Operation(summary = "답변 수정", description = "관리자가 기존 답변을 수정합니다.")
    @PutMapping("/answers/{answerId}")
    public ResponseEntity<?> updateAnswer(@PathVariable Long answerId, @RequestBody AnswerRequest request) {
        adminAnswerService.updateAnswer(answerId, request);
        return ApiResponse.updatedSuccess("답변이 성공적으로 수정되었습니다.");
    }

    // 답변 삭제
    @Operation(summary = "답변 삭제", description = "관리자가 답변을 삭제합니다. 삭제 후 문의글 상태는 '답변대기'로 변경됩니다.")
    @DeleteMapping("/answers/{answerId}")
    public ResponseEntity<?> deleteAnswer(@PathVariable Long answerId) {
        adminAnswerService.deleteAnswer(answerId);
        return ApiResponse.deletedSuccess("답변이 성공적으로 삭제되었습니다.");
    }
}
