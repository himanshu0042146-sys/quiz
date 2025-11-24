package com.ajackus.quiz.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicQuizResponse {
    public Long id;
    public String title;
    public List<QuestionDto> questions;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class QuestionDto {
        public Long id;
        public String text;
        public String type;
        public List<String> options;  // no correct answers exposed
    }
}
