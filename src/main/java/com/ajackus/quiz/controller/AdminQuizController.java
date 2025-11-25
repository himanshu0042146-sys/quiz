package com.ajackus.quiz.controller;

import com.ajackus.quiz.model.request.CreateQuizRequest;
import com.ajackus.quiz.model.request.EditQuizRequest;
import com.ajackus.quiz.model.request.EditQuizResponse;
import com.ajackus.quiz.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/quiz")
public class AdminQuizController {

    private final QuizService quizService;

    public AdminQuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("/createQuiz")
    public ResponseEntity<?> createQuiz(@RequestBody CreateQuizRequest request) {
        return ResponseEntity.ok(quizService.createQuiz(request));
    }

    @PutMapping("editQuiz/{quizId}")
    public ResponseEntity<EditQuizResponse> editQuiz(
            @PathVariable Long quizId,
            @RequestBody EditQuizRequest req) {

        EditQuizResponse resp = quizService.editQuiz(quizId, req);
        return ResponseEntity.ok(resp);
    }
}
