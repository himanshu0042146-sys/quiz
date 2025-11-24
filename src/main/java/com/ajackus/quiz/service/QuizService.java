package com.ajackus.quiz.service;

import com.ajackus.quiz.model.request.CreateQuizRequest;
import com.ajackus.quiz.model.request.SubmissionRequest;
import com.ajackus.quiz.model.response.PublicQuizResponse;
import com.ajackus.quiz.model.response.QuizResultResponse;

public interface QuizService {

    Long createQuiz(CreateQuizRequest request);

    PublicQuizResponse getQuizForPublic(Long id);

    QuizResultResponse submitQuiz(Long quizId, SubmissionRequest submission);
}