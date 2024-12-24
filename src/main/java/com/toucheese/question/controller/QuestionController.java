package com.toucheese.question.controller;

import com.toucheese.global.data.ApiResponse;
import com.toucheese.question.dto.QuestionDetailResponse;
import com.toucheese.question.dto.QuestionRequest;
import com.toucheese.question.dto.QuestionResponse;
import com.toucheese.question.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/questions")
@Tag(name = "문의하기 API")
@PreAuthorize("isAuthenticated()")
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping
    @Operation(summary = "문의하기 글 생성", description = """
        생성할 때 answerStatus ( 답변상태 ) 는 '답변대기' 로 자동으로 설정됩니다. <br>
        ```json
        {
            "title": "문의글 제목",
            "content": "문의글 내용",
            "createDate": "문의글 작성 날짜"
        }
        """
    )
    public ResponseEntity<?> createQuestion(@RequestBody QuestionRequest questionRequest, Principal principal) {
        questionService.createQuestion(questionRequest, principal);
        return ApiResponse.createdSuccess("문의하기 글이 성공적으로 생성되었습니다.");
    }

    @GetMapping("/{questionId}")
    @Operation(summary = "특정 문의하기 글 조회", description = """
        ```json
        {
            "id": "문의글 ID",
            "title": "문의글 제목",
            "content": "문의글 내용",
            "createDate": "문의글 작성 날짜",
            "answerStatus": "답변 상태"
            "answer": {
                "content": "답변 내용",
                "createDate": "답변 작성 날짜"
            }            
        }
        """
    )
    public ResponseEntity<QuestionDetailResponse> getQuestionById(@PathVariable Long questionId, Principal principal){
        QuestionDetailResponse response = questionService.findQuestionDetailById(questionId);
        return ApiResponse.getObjectSuccess(response);
    }

    @GetMapping
    @Operation(summary = "자신의 모든 문의하기 글 조회 ( 페이징 처리 )", description = """
        ```json
        {
            "id": "문의글 ID",
            "title": "문의글 제목",
            "content": "문의글 내용",
            "createDate": "문의글 작성 날짜",
            "answerStatus": "답변 상태"
        }
        """
    )
    public ResponseEntity<Page<QuestionResponse>> getQuestions(Principal principal, @RequestParam int page) {
        Page<QuestionResponse> questions = questionService.findQuestions(principal, page);
        return ApiResponse.getObjectSuccess(questions);
    }

    @PutMapping("/{questionId}")
    @Operation(summary = "문의하기 글 수정", description = """
        제목 , 내용을 수정합니다. <br>
        ```
        {
            "id": "수정할 문의글 ID",
            "title": "수정된 제목",
            "content": "수정된 내용"
        }
        """
    )
    public ResponseEntity<?> updateQuestion(@PathVariable Long questionId, @RequestBody QuestionRequest questionRequest, Principal principal) {
        questionService.updateQuestion(questionId, questionRequest, principal);
        return ApiResponse.updatedSuccess("문의하기 글이 성공적으로 수정되었습니다.");
    }

    // 게시글 삭제
    @DeleteMapping("/{questionId}")
    @Operation(summary = "문의하기 글 삭제", description = """
        ```json
        {
            "id": "삭제할 문의글 ID"
        }
        """
    )
    public ResponseEntity<?> deleteQuestion(@PathVariable Long questionId, Principal principal) {
        questionService.deleteQuestion(questionId, principal);
        return ApiResponse.deletedSuccess("문의하기 글이 성공적으로 삭제되었습니다.");
    }
}
