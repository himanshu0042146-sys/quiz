package com.ajackus.quiz.controller;

import com.ajackus.quiz.model.request.CreateQuizRequest;
import com.ajackus.quiz.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
