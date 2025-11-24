package com.ajackus.quiz.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateQuizRequest {
    public String title;
    public List<QuestionDto> questions;

    @Data
    public static class QuestionDto {
        public String text;
        public String type;   // MCQ, TRUE_FALSE, TEXT
        public List<OptionDto> options;
    }
    @Data
    public static class OptionDto {
        public String value;
        public boolean correct;
    }
}
