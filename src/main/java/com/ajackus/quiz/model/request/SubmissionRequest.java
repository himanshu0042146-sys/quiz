package com.ajackus.quiz.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionRequest {
    public List<Answer> answers;

    @Data
    public static class Answer {
        public Long questionId;
        public List<String> response;   // works for MCQ, True/False, Text
    }
}
