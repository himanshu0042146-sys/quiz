package com.ajackus.quiz.controller;

import com.ajackus.quiz.model.request.SubmissionRequest;
import com.ajackus.quiz.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz")
public class PublicQuizController {

    private final QuizService quizService;

    public PublicQuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/getQuiz/{id}")
    public ResponseEntity<?> getQuiz(@PathVariable Long id) {
        return ResponseEntity.ok(quizService.getQuizForPublic(id));
    }

    @PostMapping("/{id}/submit")
    public ResponseEntity<?> submitQuiz(@PathVariable Long id, @RequestBody SubmissionRequest submissionRequest) {
        return ResponseEntity.ok(quizService.submitQuiz(id, submissionRequest));
    }
}