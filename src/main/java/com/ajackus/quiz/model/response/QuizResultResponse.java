package com.ajackus.quiz.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class QuizResultResponse {
    public int totalQuestions;
    public int correct;
    public double score;
}