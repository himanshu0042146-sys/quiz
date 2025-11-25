package com.ajackus.quiz.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditQuizResponse {
    public Long id;
    public String title;
    public int totalQuestions;
}
