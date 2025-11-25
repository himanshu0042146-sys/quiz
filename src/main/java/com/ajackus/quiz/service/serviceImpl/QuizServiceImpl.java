package com.ajackus.quiz.service.serviceImpl;

import com.ajackus.quiz.model.request.CreateQuizRequest;
import com.ajackus.quiz.model.request.EditQuizRequest;
import com.ajackus.quiz.model.request.EditQuizResponse;
import com.ajackus.quiz.model.request.SubmissionRequest;
import com.ajackus.quiz.model.response.PublicQuizResponse;
import com.ajackus.quiz.model.response.QuizResultResponse;
import com.ajackus.quiz.entity.Option;
import com.ajackus.quiz.entity.Question;
import com.ajackus.quiz.entity.QuestionType;
import com.ajackus.quiz.entity.Quiz;
import com.ajackus.quiz.repository.OptionRepository;
import com.ajackus.quiz.repository.QuestionRepository;
import com.ajackus.quiz.repository.QuizRepository;
import com.ajackus.quiz.service.QuizService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepo;
    private final QuestionRepository questionRepo;
    private final OptionRepository optionRepo;

    public QuizServiceImpl(QuizRepository quizRepo, QuestionRepository questionRepo, OptionRepository optionRepo) {
        this.quizRepo = quizRepo;
        this.questionRepo = questionRepo;
        this.optionRepo = optionRepo;
    }

    @Override
    public Long createQuiz(CreateQuizRequest request) {
        Quiz quiz = new Quiz();
        quiz.setTitle(request.getTitle());

        request.getQuestions().forEach(questionDto -> {
            Question question = new Question();
            question.setType(QuestionType.valueOf(questionDto.getType()));
            question.setQuiz(quiz);

            if (questionDto.getOptions() != null) {
                questionDto.getOptions().forEach(eachOption -> {
                    Option option = new Option();
                    option.setOptionText(eachOption.getValue());
                    option.setCorrect(eachOption.isCorrect());
                    option.setQuestion(question);
                    question.getOptions().add(option);
                });
            }
            quiz.getQuestions().add(question);
        });
        return quizRepo.save(quiz).getId();
    }

    @Override
    public PublicQuizResponse getQuizForPublic(Long id) {
        Quiz quiz = quizRepo.findById(id).orElseThrow();

        PublicQuizResponse resp = new PublicQuizResponse();
        resp.setId(quiz.getId());
        resp.setTitle(quiz.getTitle());
        resp.setQuestions(quiz.getQuestions().stream().map(q -> {
            PublicQuizResponse.QuestionDto quizResponse = new PublicQuizResponse.QuestionDto();
            quizResponse.setId( q.getId());
            quizResponse.setText(q.getText());
            quizResponse.setType(q.getType().name());
            quizResponse.setOptions(q.getOptions().stream().map(Option::getOptionText).toList());
            return quizResponse;
        }).toList());

        return resp;
    }

    @Override
    public QuizResultResponse submitQuiz(Long quizId, SubmissionRequest submission) {

        Quiz quiz = quizRepo.findById(quizId).orElseThrow();
        int correctCount = 0;

        for (SubmissionRequest.Answer answer : submission.getAnswers()) {
            Question q = questionRepo.findById(answer.getQuestionId()).orElseThrow();

            var correctOptions = q.getOptions().stream()
                    .filter(Option::isCorrect)
                    .map(Option::getOptionText)
                    .toList();

            if (correctOptions.containsAll(answer.getResponse()) &&
                    answer.getResponse().containsAll(correctOptions)) {
                correctCount++;
            }
        }

        QuizResultResponse result = new QuizResultResponse();
        result.setTotalQuestions(quiz.getQuestions().size());
        result.setCorrect(correctCount);
        double score = (correctCount * 100.0) / result.getTotalQuestions();
        result.setScore(score);
        return result;
    }

    @Override
    public EditQuizResponse editQuiz(Long quizId, EditQuizRequest request) {

        Quiz quiz = quizRepo.findById(quizId)
                .orElseThrow(() -> new EntityNotFoundException("Quiz not found: " + quizId));

        // Update title
        if (request.getTitle() != null) {
            quiz.setTitle(request.getTitle());
        }

        // Map existing questions by id
        Map<Long, Question> existingQuestions = quiz.getQuestions().stream()
                .filter(q -> q.getId() != null)
                .collect(Collectors.toMap(Question::getId, Function.identity()));

        List<Question> updatedQuestions = new ArrayList<>();

        if (request.getQuestions() != null) {

            for (EditQuizRequest.QuestionDto qdto : request.getQuestions()) {

                Question question;
                if (qdto.getId() != null && existingQuestions.containsKey(qdto.getId())) {

                    // Update existing question
                    question = existingQuestions.remove(qdto.getId());
                    question.setText(qdto.getQuestionText());
                    question.setType(QuestionType.valueOf(qdto.getType()));

                    // Clear old options → recreate
                    question.getOptions().clear();

                } else {
                    // New question
                    question = new Question();
                    question.setQuiz(quiz);
                    question.setText(qdto.getQuestionText());
                    question.setType(QuestionType.valueOf(qdto.getType()));
                }

                // Recreate options (since DTO has no option IDs)
                List<Option> newOptions = new ArrayList<>();
                if (qdto.getOptions() != null) {
                    for (EditQuizRequest.OptionDto odto : qdto.getOptions()) {

                        Option opt = new Option();
                        opt.setOptionText(odto.getValue());
                        opt.setCorrect(odto.isCorrect());
                        opt.setQuestion(question);

                        newOptions.add(opt);
                    }
                }

                question.setOptions(newOptions);
                updatedQuestions.add(question);
            }
        }

        // Remove deleted questions
        if (!existingQuestions.isEmpty()) {
            for (Question removeQ : existingQuestions.values()) {
                quiz.getQuestions().remove(removeQ);
                // If orphanRemoval = true, no need to delete manually
            }
        }

        // Replace with updated list
        quiz.setQuestions(updatedQuestions);

        Quiz saved = quizRepo.save(quiz);
        // Build response
        EditQuizResponse resp = new EditQuizResponse();
        resp.setId(saved.getId());
        resp.setTitle(saved.getTitle());
        resp.setTotalQuestions(saved.getQuestions().size());

        return resp;
    }


    @Override
    public void deleteById(Long id) {
        Quiz quiz = quizRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Quiz not found with ID: " + id));

        quizRepo.delete(quiz);
    }

    @Override
    public void deleteByTitle(String title) {
        Quiz quiz = quizRepo.findByTitle(title)
                .orElseThrow(() -> new EntityNotFoundException("Quiz not found with title: " + title));

        quizRepo.delete(quiz);
    }
}